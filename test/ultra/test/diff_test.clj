(ns ultra.test.diff-test
  (:require [clojure.test :refer :all]
            [ultra.test.diff :as diff]))

(deftest list-diff-works
  (is (= (diff/list-diff [1 2] [2 1])
         "lists seem to contain the same items, but with different ordering."))
  (is (= (diff/list-diff [1 1] [1])
         "some duplicate items in actual are not in expected."))
  (is (= (diff/list-diff [1] [1 1])
         "some duplicate items in expected are not in actual."))
  (is (= (diff/list-diff [1] [1 2])
         "expected is longer than actual."))
  (is (= (diff/list-diff [1 2] [1])
         "actual is longer than expected.")))

(deftest list-diff-works-with-lists
  (is (= (diff/list-diff (list 1 2) (list 2 1))
         "lists seem to contain the same items, but with different ordering.")))
