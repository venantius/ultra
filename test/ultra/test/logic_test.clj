(ns ultra.test.logic-test
  (:require
    [clojure.test :refer :all]
    [ultra.test :as ultra-test]
    [ultra.test.logic :as logic]))

(ultra-test/activate!)

(deftest quote-logic-test
  (is (= 'false
         (logic/quote-logic 'false)))
  (is (= `(list ~''and (~'= 1 1) (list ~''and (list ~''or nil 1)))
         (logic/quote-logic '(and (= 1 1) (and (or nil 1)))))))

(deftest assert-logic-test
  (is (logic/assert-logic "literal true" true))
  (is (logic/assert-logic "and" (and true)))
  (is (logic/assert-logic "not" (not false)))
  (is (logic/assert-logic "complex logic" (or (and true (= 1 1)) false)))

  (let [call-count (atom 0)
        side-effect (fn []
                      (swap! call-count inc)
                      nil)]
    (is (and :foo (not (side-effect))))
    (is (= 1 @call-count)))

  (let [call-count (atom 0)
        side-effect (fn []
                      (swap! call-count inc)
                      true)]
    (is (or (side-effect) (side-effect) (side-effect)))
    (is (= 1 @call-count))))
