(ns ultra.hardcore
  (:require [robert.hooke :refer [add-hook]]
            [ultra.repl :as repl]))

(def configured? (atom {}))

(defn run-configuration
  []
  (repl/configure-repl!)
  (println "Configurated!"))

(defn configure! []
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration))))
