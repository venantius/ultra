(defproject example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [nrepl "0.6.0"]
                 #_[mvxcvi/whidbey "2.0.1-SNAPSHOT"] [johannesloetzsch/whidbey "2.0.1-SNAPSHOT"]]  ;; not yet released by mvxcvi
  :plugins [[venantius/ultra "0.6.0-SNAPSHOT"]]
  :middleware [ultra.plugin/middleware]
  :repl-options {:init-ns example.core
                 :custom-init (example.core/init)})
