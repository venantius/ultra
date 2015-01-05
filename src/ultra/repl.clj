(ns ultra.repl
  (:require [clojure.tools.nrepl.middleware.render-values :refer [render-values]]
            [clojure.tools.nrepl.server]
            [io.aviso.nrepl :refer [pretty-middleware]]
            [robert.hooke :refer [add-hook]]))

(defn add-middleware
  "Alter the default handler to include the provided middleware"
  [middleware]
  (alter-var-root
    #'clojure.tools.nrepl.server/default-handler
    partial
    middleware))

(defn add-whidbey-middleware
  "Add Whidbey's render-values middleware."
  []
  (add-middleware #'render-values))

(defn add-pretty-middleware
  "Add Aviso's Pretty middleware."
  []
  #_(add-middleware #'pretty-middleware))

(defn configure-repl!
  "Was the fn name not clear enough?"
  []
  (add-whidbey-middleware)
  (add-pretty-middleware)
  )
