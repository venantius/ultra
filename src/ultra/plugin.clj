(ns ultra.plugin
  (:require [leiningen.core.project :as project]
            [ultra.reflect]
            [whidbey.plugin :as plugin]))

(defn add-repl-middleware
  "Check to see if we need to add nREPL middleware, and if so, add it."
  {:added "0.2.0"}
  [project {:keys [repl] :as opts}]
  (if (not (false? repl))
   (update-in project [:repl-options] merge
              {:nrepl-middleware `[clojure.tools.nrepl.middleware.render-values/render-values]
               :nrepl-context {:interactive-eval `{:renderer whidbey.render/render-str}}})
    project))

(defn add-java-functions
  "Add Java utility functions, maybe."
  {:added "0.2.0"}
  [project {:keys [java] :as opts}]
  (if (not (false? java))
    (update-in project [:repl-options] merge
               {:init `(ultra.reflect/require-java-functions)})
    project))

(defn add-ultra
  "Add ultra as a project dependency and inject configuration."
  {:added "0.1.0"}
  [project]
  (let [opts (-> (:ultra project)
                 (assoc :print-meta false
                        :map-delimiter ""
                        :sort-keys true))
        project (-> project
                    (update-in [:dependencies] concat
                               `[[mvxcvi/puget "0.6.6"]
                                 [mvxcvi/whidbey "0.4.2"]
                                 [venantius/ultra "0.1.9"]
                                 [jonase/eastwood "0.2.1"]])
                    (update-in [:injections] concat
                               `[(require 'ultra.hardcore)
                                 (ultra.hardcore/configure! ~opts)])
                    (add-repl-middleware opts)
                    (add-java-functions opts))]
    project))

(def middleware-loaded? (atom {}))

(defn middleware
  "Ultra's middleware re-writes the project map."
  {:added "0.1.0"}
  [project]
  (add-ultra project))
