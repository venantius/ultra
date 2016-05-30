(ns ultra.test.logic
  (:require [clojure.test :as test]
            [clojure.string :as s]
            [ultra.printer :refer [cprint]]
            [ultra.test.diff :refer [pretty]]))

(def logic-ops
  #{'not 'and 'or})

(defn quote-logic [form]
  (if (and (seq? form) (logic-ops (first form)))
    `(list '~(first form) ~@(map quote-logic (rest form)))
    form))

(defn assert-logic [msg form]
  `(let [result# ~form]
     (test/do-report
       {:type (if result# :pass :fail)
        :message ~msg
        :expected '~form
        :actual result#
        :with-values ~(quote-logic form)})
     result#))

(defn assert-predicate [msg form]
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

(defn maybe-print-values [{:keys [with-values]}]
  (when with-values
    (println
      "\n  values:"
      (pretty with-values))))
