(ns ultra.hardcore
  "See what I did with the ns name?"
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [robert.hooke :refer [add-hook]]
            [ultra.colorscheme :refer [set-colorscheme]]
            [ultra.repl :as repl]
            [ultra.stacktrace :as stacktrace]
            [ultra.test]))

(def configured? (atom {}))

(defn run-configuration
  [opts]
  (set-colorscheme opts)
  (repl/configure-repl!)
  (stacktrace/configure-stacktraces!)
  (ultra.test/activate!))

(defn configure! [opts]
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration opts))))
