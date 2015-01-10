(ns ultra.test.diff
  (:require [clojure.string :as s]
            [puget.ansi :as ansi]
            [ultra.printer :refer [cprint]]
            [ultra.test.diff.string :as str-diff]
            [ultra.util :as util]))

(defn indent-map-string
  "Replace all newline breaks with whitespace for left-padding"
  [x]
  (apply str
         (drop-last
           3
           (s/replace
             x #"\n" (str "\n          ")))))

(defn print-expected
  [actual expected]
  (print "\nexpected: ")
  (cprint expected)
  (print "  actual: ")
  (cprint actual))

(defn print-expected-map
  [actual expected]
  (let [expected (s/trim (indent-map-string (with-out-str (cprint expected))))
        actual (s/trim (indent-map-string (with-out-str (cprint actual))))]
  (println "\nexpected:" expected)
  (println "  actual:" actual)))

(defmulti prn-diffs
  "Multimethod for printing results when the values from an is equality
  form return false (e.g. `(is (= 1 2))). Pretty-prints differently depending
  on the values involved."
  (fn [a b actual expected]
    (cond
      (and (string? a) (string? b)) ::diff-str
      (not= (class a) (class b)) ::wrong-class
      :default [a b actual expected])))

(defmethod prn-diffs ::diff-str [a b actual expected]
  (print-expected actual expected)
  (print "\n    diff:")
  (print (str-diff/clean-difform-str a b)))

(defmethod prn-diffs ::wrong-class [a b actual expected]
  (print-expected actual expected)
  (let [puget-str (fn [x] (s/trim-newline (with-out-str (cprint x))))]
    (println)
    (println "expected:" (puget-str a) "to be an instance of" (class b))
    (println "     was:" (puget-str a) "is an instance of" (class a))))



(defmethod prn-diffs :default [a b actual expected]
  (print-expected-map actual expected)
  (print "\n    diff:")
  (if a
    (do (print (ansi/sgr " - " :red))
        (let [a (with-out-str (cprint a))]
          (print (apply str (drop-last 3 (s/replace
                   a #"\n" (str "\n            "))))))
        (print (ansi/sgr " + " :green)))
    (print (ansi/sgr " + " :green)))
  (when b
    (let [b (with-out-str (cprint b))]
      (print (apply
               str
               (drop-last
                 3
                 (s/replace
                   b #"\n" (str "\n            "))))))))
