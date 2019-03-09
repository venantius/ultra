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
    (whidbey.plugin/repl-pprint project)
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

(defn add-ultra
  "Add ultra as a project dependency and inject configuration."
  {:added "0.1.0"}
  [project opts]
  (-> project
      (plugin/add-dependencies
       (plugin/plugin-dependency project 'venantius/ultra))
      (update-in [:injections] concat `[(require 'ultra.hardcore)
                                        (ultra.hardcore/add-test-hooks! ~opts)])
      (assoc :monkeypatch-clojure-test false)
      (add-repl-middleware opts)
      (inject-repl-initialization opts)))

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
