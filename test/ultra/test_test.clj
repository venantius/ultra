(ns test.ultra.test-test
  (:require [clojure.test :refer :all]))

(deftest exception-is-thrown
  (is (contains? 5 :b)))

(deftest types-that-don't-match
  (is (= "1" 2)))

(deftest comparing-maps
  (is (= {:alpha "Thing"
          :gamma "Triple thing"}
         {:alpha "Thing"
          :beta "Other thing"})))

(deftest comparing-things
  (is (true? true))
  (is (false? true)))
