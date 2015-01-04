(ns ultra.repl
  (:require [clojure.tools.nrepl.middleware.render-values :refer [render-values]]
            [clojure.tools.nrepl.middleware :as middleware]
            [clojure.tools.nrepl.server]
            [robert.hooke :refer [add-hook]]
            ))

(def default-cprint-handler
  (partial
    clojure.tools.nrepl.server/default-handler
    #'render-values))

(defn add-whidbey-middleware
  "Intern `(default-handler)` in `clojure.tools.nrepl.server` as a partial
  of itself with the Whidbey render-values middleware included."
  []
  (intern 'clojure.tools.nrepl.server 'default-handler #'default-cprint-handler))

(defn configure-repl!
  "Was the fn name not clear enough?"
  []
  (add-whidbey-middleware)
  (println "Configured repl!")
  )
