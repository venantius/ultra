(ns ultra.plugin-test
  (:require [bond.james :as bond :refer [with-spy]]
            [clojure.test :refer :all]
            [ultra.plugin :as plugin]))

(def project
  {:description "Ultra: A Leiningen plugin for a superior development environment"
   :url "http://github.com/venantius/ultra"
   :license {:name "Eclipse Public License"
             :url "http://www.eclipse.org/legal/epl-v10.html"}
   :dependencies [['org.clojure/clojure "1.8.0"]
                  ['org.clojure/tools.nrepl "0.2.12"]

                  ['grimradical/clj-semver "0.3.0" :exclusions ['org.clojure/clojure]]
                  ['im.chit/hara.class "2.2.15"]
                  ['im.chit/hara.reflect "2.2.15"]
                  ['io.aviso/pretty "0.1.24"]
                  ['mvxcvi/whidbey "1.3.0"]
                  ['mvxcvi/puget "1.0.0"]
                  ['org.clojars.brenton/google-diff-match-patch "0.1"]
                  ['robert/hooke "1.3.0"]
                  ['venantius/glow "0.1.3"]]
   :test-selectors {:default (complement :demo)
                    :demo :demo}
   :min-lein-version "2.5.2"
   :eval-in-leiningen true})

(deftest middleware-sets-repl-opts
  (with-spy [plugin/add-ultra]
    (plugin/middleware project)
    (let [passed-opts (-> plugin/add-ultra bond/calls first :args second)]
      (= passed-opts
         {:repl {:print-meta false
                 :map-delimiter ""
                 :print-fallback :print
                 :sort-keys true}})))
  (with-spy [plugin/add-ultra]
    (plugin/middleware (assoc project :ultra {:repl {:print-meta true}}))
    (let [passed-opts (-> plugin/add-ultra bond/calls first :args second)]
      (= passed-opts
         {:repl {:print-meta true
                 :map-delimiter ""
                 :print-fallback :print
                 :sort-keys true}}))))
