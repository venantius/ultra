(ns ultra.stacktrace
  (:require [pyro.printer :as printer]
            [clojure.stacktrace :as st]))

(defn configure-stacktraces!
  "Configure the printing of stacktraces for exceptions.

  As of 0.6.0, uses Pyro as a stacktrace engine. Prior to that, Ultra used
  Aviso's Pretty."
  {:added "0.1.1"
   :updated "0.5.3"}
  [opts]
  (reset! printer/options opts)
  (alter-var-root
   #'st/print-stack-trace
   (constantly (partial printer/pprint-exception {})))
   ;; used by clojure.test
  (alter-var-root
   #'st/print-cause-trace
   (constantly (partial printer/pprint-exception opts))))
