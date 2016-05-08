(ns ^:demo ultra.test-test
  "More comprehensive tests for screenshot purposes."
  (:require [clojure.test :refer :all]))

(deftest default-comparison-large
  (let [long-vector [:id
                     :other-id
                     :third-id
                     :you-tired-of-ids-yet
                     :no?
                     :not-yet?
                     :well-how-about-some-more?
                     :howd-you-like-them-apples!]]
    (is (every? (set (keys {:a 1 :b 5})) long-vector))))

(deftest logical-expressions-are-preserved
  (let [pirate {:name "Edward Teach"
                :nickname "Blackbeard"
                :rating :notorious}]
    (is (and (:name pirate)
             (or (empty? pirate) (:age pirate))))
    (is (not (= (:rating pirate) :notorious)))))
