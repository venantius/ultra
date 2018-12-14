(ns ultra.repl.whidbey
  "Unfortunately, Whidbey has some import side-effects that require us to
  dynamically import this namespace."
  (:require [clojure.tools.nrepl.middleware.render-values :refer [render-values]]
            ;; note that the above dependency comes from Whidbey, not nREPL.
            [ultra.repl :refer [add-middleware]]))

(defn add-whidbey-middleware
  "Add Whidbey's render-values middleware."
  {:added "0.1.0"}
  []
  (add-middleware #'render-values))
