(ns ultra.test
  (:use clojure.test)
  (:require [clojure.data :as data]
            [clojure.pprint :as pp]
            [puget.color.ansi :as ansi]
            [puget.printer :as printer]
            [ultra.test.diff :as diff]
            [whidbey.render :as render]))

(defn generate-diffs
  [a more]
  (map vector
       more
       (map #(take 2 (data/diff a %))
            more)))

(defmethod assert-expr '= [msg [_ a & more]]
  `(let [a# ~a]
     (if-let [more# (seq (list ~@more))]
       (let [result# (apply = a# more#)]
         (if result#
           (do-report {:type :pass, :message ~msg,
                       :expected a#, :actual more#})
           (do-report {:type :fail, :message ~msg,
                       :expected a#, :actual more#,
                       :diffs (generate-diffs a# more#)}))
         result#)
       (throw (Exception. "= expects more than one argument")))))

(defmethod report :fail
  [{:keys [type expected actual diffs message] :as event}]
  (with-test-out
    (inc-report-counter :fail)
    (println (str (ansi/sgr "\nFAIL" :red) " in " (testing-vars-str event)))
    (when (seq *testing-contexts*) (println (testing-contexts-str)))
    (when message (println message))
    (if (seq diffs)
      (doseq [[actual [a b]] diffs
              :when (or a b)]
        (diff/prn-diffs a b actual expected))
      (diff/print-expected actual expected))))
