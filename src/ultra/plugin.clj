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

(defn add-legacy-repl-middleware
  "Check to see if we need to add nREPL middleware, and if so, add it.

  LEGACY"
  {:added "0.4.1"}
  [project {:keys [repl] :as opts}]
  (if (not (false? repl))
    (-> project
        (plugin/set-interactive-eval-renderer
         'whidbey.render/render-str)
        (plugin/add-nrepl-middleware
         `clojure.tools.nrepl.middleware.render-values/render-values))
    project))

(defn inject-repl-initialization
  "Move most configuration into REPL initialization."
  {:added "0.3.0"}
  [project {:keys [repl] :as opts}]
  (let [whidbey-opts (:whidbey project)]
    (plugin/add-repl-init
      project
      (if repl
        `(do (require 'ultra.hardcore)
             (require 'whidbey.repl)
             (whidbey.repl/init! ~whidbey-opts)
             (ultra.hardcore/configure! ~opts))
        `(do (require 'ultra.hardcore)
             (ultra.hardcore/configure! ~opts))))))

(defn add-ultra-legacy
  "If this project doesn't support reader conditionals, inject Ultra 0.3.4 and
  emit a warning."
  {:added "0.4.1"}
  [project opts]
  (defonce warn
    (println (format (str "Warning: the Clojure version for this project (%s) "
                          "does not support reader conditionals. Ultra is "
                          "falling back to version 0.3.4.")
                     (second (some plugin/clojure-dep? (:dependencies project))))))
  (let [opts (merge {:color-scheme :solarized_dark} opts)]
    (-> project
        (plugin/remove-plugin 'venantius/ultra)
        (plugin/remove-dependency 'mvxcvi/puget)
        (plugin/remove-dependency 'mvxcvi/whidbey)
        (plugin/remove-dependency 'im.chit/hara.class)
        (plugin/remove-dependency 'im.chit/hara.reflect)
        (plugin/add-plugins
         ['venantius/ultra "0.3.4"])
        (plugin/add-dependencies
         ['mvxcvi/puget "0.8.1"]
         ['venantius/ultra "0.3.4"]
         ['mvxcvi/whidbey "1.0.0"]
         ['im.chit/hara.class "2.1.8"]
         ['im.chit/hara.reflect "2.1.8"])
        (update-in [:injections] concat `[(require 'ultra.hardcore)
                                          (ultra.hardcore/add-test-hooks! ~opts)])
        (assoc :monkeypatch-clojure-test false)
        (add-legacy-repl-middleware opts)
        (inject-repl-initialization opts))))

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
        (add-ultra-legacy opts))))

(defn middleware
  "Ultra's middleware re-writes the project map."
  {:added "0.1.0"}
  [project]
  (let [default-whidbey-opts {:print-meta false
                              :map-delimiter ""
                              :print-fallback :print
                              :sort-keys true}
        repl (-> project :ultra :repl)
        repl-opts (if (false? repl)
                    repl
                    (if (true? repl)
                      default-whidbey-opts
                      (merge default-whidbey-opts repl)))
        opts (-> (:ultra project)
                 (assoc :repl repl-opts))]
    (add-ultra project opts)))
