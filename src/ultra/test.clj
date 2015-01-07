(ns ultra.test
  (:use clojure.test)
  (:require [clojure.data :as data]
            [clojure.pprint :as pp]
            [pjstadig.humane-test-output :as hto]
            [puget.ansi :as ansi]
            [puget.printer :as printer]
            [whidbey.render :as render]))

(defn without-meta
  "If the object contains metadata, remove it"
  [x]
  (if (meta x)
    (with-meta x nil)
    x))

(defn cprint
  [x]
  (printer/cprint x render/puget-options))

(defn print-expected
  [actual expected]
  (print "\nexpected: ")
  (cprint (without-meta expected))
  (print "  actual: ")
  (cprint (without-meta actual))
  (print "\n"))

(defonce activation-body
  (delay
   (when (not (System/getenv "INHUMANE_TEST_OUTPUT"))
     (defmethod assert-expr '= [msg [_ a & more]]
       `(let [a# ~a]
          (if-let [more# (seq (list ~@more))]
            (let [result# (apply = a# more#)]
              (if result#
                (do-report {:type :pass, :message ~msg,
                            :expected a#, :actual more#})
                (do-report {:type :fail, :message ~msg,
                            :expected a#, :actual more#,
                            :diffs (map vector
                                        more#
                                        (map #(take 2 (data/diff a# %))
                                             more#))}))
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
             (print-expected actual expected)
             (print "    diff:")
             (if a
               (do (print (ansi/sgr " - " :red))
                   (cprint a)
                   (print (ansi/sgr "          + " :green)))
               (print (ansi/sgr " + " :green)))
             (when b
               (cprint b)))
           (print-expected actual expected)))))))

(defn activate! []
  @activation-body)
