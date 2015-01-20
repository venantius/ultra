(ns ^:demo ultra.demo.set-test
  "Tests for sets that are intended to fail."
  (:require [clojure.test :refer :all]))

(deftest comparing-sets-1
  (is (= #{1 2 3} #{1 2 4})))
