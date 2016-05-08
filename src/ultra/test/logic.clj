(ns ultra.test.logic
  (:require [clojure.test :as test]))

(def logic-ops
  #{'not 'and 'or})

(defn quote-logic [form]
  (if (and (sequential? form) (logic-ops (first form)))
    `(list '~(first form) ~@(map quote-logic (rest form)))
    form))

(defn assert-logic [msg form]
  `(let [result# ~form]
     (test/do-report
       {:type (if result# :pass :fail)
        :message ~msg
        :expected '~form
        :actual ~(if (and (sequential? form) (= 'not (first form)))
                   (quote-logic (second form))
                   `(list '~'not ~(quote-logic form)))})
     result#))
