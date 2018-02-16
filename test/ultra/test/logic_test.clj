(ns ultra.test.logic-test
  (:require
   [clojure.test :refer :all]
   [ultra.test.logic :as logic]))

(deftest quote-logic-test
  (is (= '(quote false)
         (logic/quote-logic 'false)))
  (is (= `(list ~''and (quote (~'= 1 1)) (quote (~'list (~'and (~''or (quote nil) (quote 1))))))
         (logic/quote-logic '(and (= 1 1) (list (and ((quote or) (quote nil) (quote 1)))))))))

(deftest assert-logic-test
  (is (logic/assert-logic "literal true" true))
  (is (logic/assert-logic "and" (and true)))
  (is (logic/assert-logic "not" (not false)))
  (is (logic/assert-logic "complex logic" (or (and true (= 1 1)) false))))
