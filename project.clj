(defproject venantius/ultra "0.1.6"
  :description "Ultra: opinonated development environmnent configuration"
  :url "http://github.com/venantius/ultra"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.nrepl "0.2.6"]

                 [im.chit/iroh "0.1.11"]
                 [io.aviso/pretty "0.1.14"]
                 [mvxcvi/whidbey "0.4.2"]
                 [org.clojars.brenton/google-diff-match-patch "0.1"]
                 [pjstadig/humane-test-output "0.6.0"]
                 [robert/hooke "1.3.0"]]
  :test-selectors {:default (complement :demo)
                   :demo :demo}
  :eval-in-leiningen true)
