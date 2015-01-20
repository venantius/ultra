(ns ^:demo ultra.demo.string-test
  "String tests that will fail on purpose to show diff output. Intended for makin' pretty screenshots."
  (:require [clojure.test :refer :all]))

(deftest string-comparison-1
  (is (= "spice" "spork")))

(deftest string-comparison-2
  (is (= "strings equal" "strings equal"))
  (is (= "balloon" "baloon"))
  (is (= "animal" "imal")))
