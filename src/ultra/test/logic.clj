(ns ultra.test.logic
  (:require [clojure.test :as test]
            [clojure.string :as s]
            [ultra.printer :refer [cprint]]
            [ultra.test.diff :refer [pretty]]))

(def logic-ops
  #{'not 'and 'or})

(defn quote-logic
  "Preserves logic expressions to assist debugging."
  {:added "0.4.2"}
  [form]
  (if (and (seq? form) (logic-ops (first form)))
    `(list '~(first form) ~@(map quote-logic (rest form)))
    form))

(defn assert-logic
  "Preserves useful information in the :with-values key of the test."
  {:added "0.4.2"}
  [msg form]
  `(let [result# ~form]
     (test/do-report
       {:type (if result# :pass :fail)
        :message ~msg
        :expected '~form
        :actual result#
        :with-values ~(quote-logic form)})
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
