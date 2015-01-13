(ns ultra.plugin
  (:require [leiningen.core.project :as project]
            [whidbey.plugin :as plugin]))

(defn add-ultra
  "Add ultra as a project dependency and inject configuration."
  [project]
  (let [opts (-> (:ultra project)
                 (assoc :print-meta false
                        :map-delimiter ""
                        :sort-keys true))]
    (-> project
        (update-in [:dependencies] concat
                   `[[mvxcvi/puget "0.6.6"]
                     [mvxcvi/whidbey "0.4.2"]
                     [venantius/ultra "0.1.6"]])
        (update-in [:injections] concat
                   `[(require 'ultra.hardcore)
                     (ultra.hardcore/configure! ~opts)])
        (update-in [:repl-options] merge
                   `{:nrepl-middleware [clojure.tools.nrepl.middleware.render-values/render-values]
                     :nrepl-context {:interactive-eval {:renderer whidbey.render/render-str}}}))))

(defn middleware
  "Load all Ultra middleware."
  [project]
  (-> project
      add-ultra))
