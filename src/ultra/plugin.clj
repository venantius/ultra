(ns ultra.plugin
  (:require [leiningen.core.project :as project]
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
                                 [venantius/ultra "0.2.0"]
                                 [im.chit/hara.class "2.1.8"]
                                 [im.chit/hara.reflect "2.1.8"]])
                    (update-in [:injections] concat
                               `[(require 'ultra.hardcore)
                                 (ultra.hardcore/configure! ~opts)])
                    (add-repl-middleware opts))]
    project))

(defn middleware
  "Ultra's middleware re-writes the project map."
  {:added "0.1.0"}
  [project]
  (add-ultra project))
