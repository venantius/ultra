(defproject venantius/ultra "0.3.3"
  :description "Ultra: A Leiningen plugin for a superior development environment"
  :url "http://github.com/venantius/ultra"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.nrepl "0.2.7"]

                 [io.aviso/pretty "0.1.14"]
                 [mvxcvi/whidbey "1.0.0"]
                 [mvxcvi/puget "0.8.1"]
                 [robert/hooke "1.3.0"]
                 [org.clojars.brenton/google-diff-match-patch "0.1"]]
  :test-selectors {:default (complement :demo)
                   :demo :demo}
  :eval-in-leiningen true)
