(ns ultra.repl
  (:require [clojure.main :as main]
            [clojure.repl :as repl]
            [clojure.tools.nrepl.server]
            [io.aviso.repl :as pretty-repl]))

(defn add-middleware
  "Alter the default handler to include the provided middleware"
  {:added "0.1.0"}
  [middleware]
  (alter-var-root
    #'clojure.tools.nrepl.server/default-handler
    partial
    middleware))

(defn add-pretty-middleware
  "Add Aviso's Pretty functionality"
  {:added "0.1.2"}
  []
  (alter-var-root
    #'main/repl-caught
    (constantly pretty-repl/pretty-repl-caught)) ;; should this just pretty-pst ?
  (alter-var-root
    #'repl/pst
    (constantly pretty-repl/pretty-pst)))

(defn configure-repl!
  "Was the fn name not clear enough?"
  {:added "0.1.0"}
  [repl stacktraces]
  (when (not (false? repl))
    (require 'ultra.repl.whidbey)
    (eval '(ultra.repl.whidbey/add-whidbey-middleware)))
  (when (not (false? stacktraces))
    (add-pretty-middleware)))
