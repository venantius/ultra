(ns ultra.reflect
  "A namespace for Java object reflection. Mostly aliased to hara.reflect"
  (:require [hara.class]
            [hara.reflect]
            [vinyasa.inject :refer [in]]))

(defn inject-java-functions
  "Inject various functions for Java class inspection and reflection
  into clojure.core so that they will be available in the REPL."
  {:added "0.1.7"}
  []
  (in clojure.core
      [hara.class :all]
      [hara.reflect class-info])
  (use 'clojure.core :reload))
