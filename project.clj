(defproject venantius/ultra "0.4.0-SNAPSHOT"
  :description "Ultra: A Leiningen plugin for a superior development environment"
  :url "http://github.com/venantius/ultra"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.nrepl "0.2.11"]

                 [io.aviso/pretty "0.1.18"]
                 [mvxcvi/whidbey "1.1.1"]
                 [im.chit/hara.class "2.2.11"]
                 [im.chit/hara.reflect "2.2.11"]
                 [mvxcvi/puget "0.9.2"]
                 [org.clojars.brenton/google-diff-match-patch "0.1"]
                 [robert/hooke "1.3.0"]
                 [venantius/glow "0.1.2"]]
  :test-selectors {:default (complement :demo)
                   :demo :demo}
  :min-lein-version "2.5.2"
  :eval-in-leiningen true)
