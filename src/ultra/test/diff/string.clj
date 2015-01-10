;; Copyright (c) 2010 George Jahad All rights reserved.
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0
;; (http://opensource.org/licenses/eclipse-1.0.php) which can be found
;; in the file epl-v10.html at the root of this distribution.  By
;; using this software in any fashion, you are agreeing to be bound by
;; the terms of this license.  You must not remove this notice, or any
;; other, from this software.
;; Contributors:
;; Brenton Ashworth
;; W. David Jarvis

(ns ultra.test.diff.string
  (:import name.fraser.neil.plaintext.diff_match_patch$Operation
           name.fraser.neil.plaintext.diff_match_patch)
  (:require [clojure.string :as s]
            [clojure.pprint :as pprint]
            [puget.ansi :as ansi]
            [ultra.printer :refer [cprint]])
  (:use clojure.walk))

(def diff-markers
   {diff_match_patch$Operation/EQUAL " "
    diff_match_patch$Operation/INSERT (ansi/sgr "+" :green)
    diff_match_patch$Operation/DELETE (ansi/sgr "-" :red)})

(defn- print-diff [d]
  (let [m (diff-markers (.operation d)) ]
    (print (str "          " m " "))
    (cprint (s/replace (.trim (.text d))
                        #"\n" (str "\n " m " ")))))

(defn canonical-form [f]
  (with-out-str (print f)))

(defn clean-difform [x y]
  (let [dmp (diff_match_patch.)
        diffs (.diff_main dmp
                          (canonical-form x)
                          (canonical-form y))]
    (do (.diff_cleanupSemantic dmp diffs)
        (doseq [d diffs] (print-diff d)))))

(defn clean-difform-str [x y]
  (apply str (drop 9
               (with-out-str
                 (clean-difform x y)))))
