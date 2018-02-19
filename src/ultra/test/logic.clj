(ns ultra.test.logic
  (:require [clojure.test :as test]
            [ultra.printer :refer [cprint]]
            [ultra.test.diff :refer [pretty]]))

(def logic-ops
  #{'not 'and 'or})

(defn take-to-first-true
  ([xs]
    (take-to-first-true [] xs))
  ([falsies [x & more]]
    (if x
      (conj falsies x)
      (recur (conj falsies x) more))))

(defn quote-logic
  "Preserves logic expressions to assist debugging."
  {:added "0.4.2"}
  [form]
  (if (and (seq? form) (logic-ops (first form)))
    `(list '~(first form)
           ~@(cond->> (map quote-logic (rest form))
                      (= (first form) 'or) (take-to-first-true)))
    form))

(defn assert-logic
  "Preserves useful information in the :with-values key of the test."
  {:added "0.4.2"}
  [msg form]
  `(let [result# ~(quote-logic form)]
     (test/do-report
       {:type (if result# :pass :fail)
        :message ~msg
        :expected '~form
        :actual result#
        :with-values result#})
     result#))

(defn assert-predicate
  "Replaces core.test/assert-predicate to report more useful output."
  {:added "0.4.2"}
  [msg form]
  (let [args (rest form)
        pred (first form)]
    `(let [values# (list ~@args)
           result# (apply ~pred values#)
           hint# (cons '~pred values#)]
       (test/do-report
         {:type (if result# :pass :fail)
          :message ~msg
          :expected '~form
          :actual result#
          :with-values (when (not= '~form hint#)
                         hint#)})
       result#)))

(defn maybe-print-values
  "Reports the expression values of a test result when they exist."
  {:added "0.4.2"}
  [{:keys [with-values]}]
  (when with-values
    (println
      "\n  values:"
      (pretty with-values))))
