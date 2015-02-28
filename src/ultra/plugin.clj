(ns ultra.plugin
  (:require [leiningen.core.project :as project]
            [whidbey.plugin :as plugin]))


;; clojure.tools.nrepl.middleware.render-values/render-values
(defn prepend-repl-middleware
  "Add our nREPL middleware to the front of the nREPL middleware vector.

  This is to avoid a sneaky bug with Whidbey and CIDER that's caused by
  load order."
  {:added "0.2.1"}
  [project]
  (let [current-middleware (or (-> project
                                   :repl-options
                                   :nrepl-middleware) [])
        new-middleware (reduce conj
                               [`clojure.tools.nrepl.middleware.render-values/render-values]
                               current-middleware)]
    (assoc-in project [:repl-options :nrepl-middleware] new-middleware)))

(defn add-repl-middleware
  "Check to see if we need to add nREPL middleware, and if so, add it."
  {:added "0.2.0"}
  [project {:keys [repl] :as opts}]
  (if (not (false? repl))
    (-> project
        (update-in [:repl-options] merge
                   {:nrepl-context
                    {:interactive-eval
                     `{:renderer whidbey.render/render-str}}})
        prepend-repl-middleware)
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
                                 [venantius/ultra "0.2.2"]
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
