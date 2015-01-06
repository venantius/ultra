(ns ultra.stacktrace
  (:require [io.aviso.repl :as pretty]
            [clojure
             [main :as main]
             [repl :as repl]
             [stacktrace :as st]]))

(defn configure-stacktraces!
  "Configure the printing of stacktraces for exceptions."
  []
  (alter-var-root
    #'st/print-stack-trace
    (constantly pretty/pretty-print-stack-trace))
  (alter-var-root
    #'st/print-cause-trace
    (constantly pretty/pretty-print-stack-trace)))
