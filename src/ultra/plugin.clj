(ns ultra.plugin
  (:require [leiningen.core.project :as project]
            [whidbey.plugin :as plugin]))



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
                                 (ultra.hardcore/configure! ~opts)]))]
    (if (not (false? (:repl opts)))
      (update-in project [:repl-options] merge
                 `{:nrepl-middleware [clojure.tools.nrepl.middleware.render-values/render-values]
                   :nrepl-context {:interactive-eval {:renderer whidbey.render/render-str}}})
      project)))

(def middleware-loaded? (atom {}))

(defn middleware
  "Ultra's middleware re-writes the project map."
  {:added "0.1.0"}
  [project]
  (add-ultra project))
