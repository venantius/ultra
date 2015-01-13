(ns ^:demo ultra.demo-test
  "Tests for screenshot purposes."
  (:require [clojure.test :refer :all]))

(deftest exception-is-thrown
  (is (contains? 5 :b)))

(deftest string-comparisons
  (is (= "spice" "spork")))

(deftest types-that-don't-match
  (is (= "1" 2)))

(deftest comparing-maps
  (is (= {:alpha "Thing"
          :gamma "Triple thing"}
         {:alpha "Thing"
          :beta "Other thing"})))

(deftest comparing-vecs
  (is (= [1 2 3 4 5]
         [1 2 4 5])))

(deftest comparing-things
  (is (true? true))
  (is (false? true)))

