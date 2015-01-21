(ns ultra.repl
  (:require [clojure.main :as main]
            [clojure.repl :as repl]
            [clojure.tools.nrepl.middleware.render-values :refer [render-values]]
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

(defn add-whidbey-middleware
  "Add Whidbey's render-values middleware."
  {:added "0.1.0"}
  []
  (add-middleware #'render-values))

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
  []
  (add-whidbey-middleware)
  (add-pretty-middleware))
