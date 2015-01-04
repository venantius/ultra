(ns ultra.plugin
  (:require [leiningen.core.project :as project]
            [whidbey.plugin :as plugin]))

(defn add-ultra
  "Add ultra as a project dependency and inject configuration."
  [project]
  (-> project
      (update-in [:dependencies] concat
                 `[[venantius/ultra "0.1.0"]])
      (update-in [:injections] concat
                 `[(require 'ultra.hardcore)
                   (ultra.hardcore/configure!)])
      #_(update-in [:repl-options] dissoc :nrepl-middleware)

      ))

(defn middleware
  "Load all Ultra middleware."
  [project]
  (-> project
      whidbey.plugin/middleware
      add-ultra))
