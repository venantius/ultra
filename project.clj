(defproject venantius/ultra "0.6.0-SNAPSHOT"
  :description "Ultra: A Leiningen plugin for a superior development environment"
  :url "http://github.com/venantius/ultra"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[nrepl "0.4.0"]
                 [grimradical/clj-semver "0.3.0" :exclusions [org.clojure/clojure]]
                 [io.aviso/pretty "0.1.35"]
                 [mvxcvi/whidbey "1.3.2"]
                 [mvxcvi/puget "1.1.0"]
                 [org.clojars.brenton/google-diff-match-patch "0.1"]
                 [robert/hooke "1.3.0"]
                 [venantius/glow "0.1.5" :exclusions [hiccup garden]]]
  :profiles {:dev {:dependencies [[circleci/bond "0.2.9"]
                                  [org.clojure/clojure "1.8.0"]]}}
  :test-selectors {:default (complement :demo)
                   :demo :demo}
  :min-lein-version "2.5.2"
  :eval-in-leiningen true)
