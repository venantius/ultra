(ns ultra.hardcore
  (:require [robert.hooke :refer [add-hook]]
            [ultra.repl :as repl]
            [ultra.stacktrace :as stacktrace]
            [ultra.test]))

(def configured? (atom {}))

(defn run-configuration
  []
  (repl/configure-repl!)
  (stacktrace/configure-stacktraces!)
  (ultra.test/activate!))

(defn configure! []
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration))))
