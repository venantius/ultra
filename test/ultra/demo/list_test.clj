(ns ^:demo ultra.demo.list-test
  "Tests for lists and vecs that are intended to fail."
  (:require [clojure.test :refer :all]))

(deftest comparing-vecs-1
  (is (= [1 2] [1]))
  (is (= [1] [1 2]))
  (is (= [1 1] [1])))

(deftest comparing-lists-1
  (is (= (list 1) (list 1 1)))
  (is (= (list 1 2) (list 2 1))))
