(ns ultra.ns
  (:require [ultra.util :as util]))

(defn load-eastwood-quietly
  "Because `eastwood.util` has `protocol?` and `interface?` methods, which
  Ultra injects into `clojure.core`, we want to load Eastwood quietly."
  {:added "0.1.9"}
  []
  (util/with-err-str
    (require 'eastwood.util)))

(defn load!
  "Load any namespaces that require trickery."
  {:added "0.1.9"}
  []
  (load-eastwood-quietly))
