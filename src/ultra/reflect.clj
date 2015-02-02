(ns ultra.reflect
  "A namespace for Java object reflection. Mostly aliased to hara.reflect"
  (:require [hara.class]
            [hara.reflect]))

(defn require-java-functions
  "Require various functions for Java class inspection and reflection.

  Called by Leiningen's repl-options init."
  {:added "0.2.0"}
  []
  (require '[hara.class :refer :all])
  (require '[hara.reflect :refer :all]))
