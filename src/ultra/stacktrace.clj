(ns ultra.stacktrace
  (:require [io.aviso.repl :as pretty-repl]
            [clojure.stacktrace :as st]))

(defn configure-stacktraces!
  "Configure the printing of stacktraces for exceptions."
  {:added "0.1.1"}
  []
  (alter-var-root
    #'st/print-stack-trace
    (constantly pretty-repl/pretty-print-stack-trace))
  (alter-var-root
    #'st/print-cause-trace
    (constantly pretty-repl/pretty-print-stack-trace)))
