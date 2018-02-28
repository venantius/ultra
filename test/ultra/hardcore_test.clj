(ns ultra.hardcore-test
  (:require [clojure.test :refer :all]
            [ultra.hardcore] ;; make sure this actually loads
            ))

(deftest working-test
  (is (= 1 1)))
