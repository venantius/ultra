(ns ultra.test.diff.string-test
  (:require [clojure.test :refer :all]
            [puget.color.ansi :as ansi]
            [ultra.printer :as printer]
            [ultra.test.diff.string :as st]))

(deftest whitespace-test
  (testing "That whitespace is properly diffed"
    (let [str1 "thing 1     2"
          str2 "thing 1  2"]
      (with-redefs [printer/cprint (fn [x & _] (prn x))]
        (is (=  (str "            \"thing 1  \"\n          "
                     (ansi/sgr "-" :red)
                     " \"   \"\n            \"2\"\n")
                (with-out-str (st/clean-difform str1 str2))))))))
