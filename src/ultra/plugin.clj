(ns ultra.plugin
  (:require [leiningen.core.project :as project]
            [leiningen.test]
            [robert.hooke :refer [add-hook]]
            [ultra.hardcore]
            [ultra.plugin.utils :as plugin]
            [whidbey.plugin]))

(defn add-repl-middleware
  "Check to see if we need to add nREPL middleware, and if so, add it."
  {:added "0.2.0"}
  [project {:keys [repl] :as opts}]
  (if (not (false? repl))
    (-> project
        (plugin/set-interactive-eval-renderer
         'whidbey.repl/render-str)
        (plugin/add-nrepl-middleware
         `clojure.tools.nrepl.middleware.render-values/render-values))
    project))

(defn inject-repl-initialization
  "Move most configuration into REPL initialization."
  {:added "0.3.0"}
  [project opts]
  (let [whidbey-opts (:whidbey project)]
    (plugin/add-repl-init
     project
     `(do (require 'ultra.hardcore)
          (require 'whidbey.repl)
          (whidbey.repl/init! ~whidbey-opts)
          (ultra.hardcore/configure! ~opts)))))

(defn add-ultra-legacy
  "If this project doesn't support reader conditionals, inject Ultra 0.3.4 and
  emit a warning."
  {:added "0.4.1"}
  [project]
  (defonce warn
    (println (format (str "Warning: the Clojure version for this project (%s) "
                          "does not support reader conditionals. Ultra is"
                          "falling back to version 0.3.4.")
                     (second (some plugin/clojure-dep? (:dependencies project))))))
  (plugin/add-dependencies
   project
   ['venantius/ultra "0.3.4"]))

(defn add-ultra
  "Add ultra as a project dependency and inject configuration."
  {:added "0.1.0"}
  [project opts]
  (if (plugin/supports-cljc? project)
    (-> project
        (plugin/add-dependencies
         (plugin/plugin-dependency project 'venantius/ultra))
        (update-in [:injections] concat `[(require 'ultra.hardcore)
                                          (ultra.hardcore/add-test-hooks! ~opts)])
        (assoc :monkeypatch-clojure-test false)
        (add-repl-middleware opts)
        (inject-repl-initialization opts))
    (-> project
        add-ultra-legacy
        (plugin/remove-plugin 'venantius/ultra))))

(defn middleware
  "Ultra's middleware re-writes the project map."
  {:added "0.1.0"}
  [project]
  (let [opts (-> (:ultra project)
                 (assoc :print-meta false
                        :map-delimiter ""
                        :print-fallback :print
                        :sort-keys true))]
    (add-ultra project opts)))
