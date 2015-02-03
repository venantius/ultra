(ns ultra.hardcore
  "See what I did there?"
  (:require [clojure.tools.nrepl.server]
            [robert.hooke :refer [add-hook]]
            [ultra.colorscheme :as colorscheme]
            [ultra.repl :as repl]
            [ultra.stacktrace :as stacktrace]
            [ultra.test :as test]))

(def configured? (atom {}))

(defn require-java-functions
  "Start our nREPL server, then require our Java utility namespaces"
  {:added "0.2.0"}
  [f sub & opts]
  (let [server (apply (partial f sub) opts)]
    (eval '(do (require '[hara.class :refer :all])
               (require '[hara.reflect :refer :all])))
    server))

(defn java-hooks
  "Hook our Java imports into starting an nREPL server. This seems like
  an odd way of doing this, but I haven't been able to find another way
  of requiring the `hara` namespaces that doesn't result in loud warnings
  all over the place for, say, eastwood or potemkin."
  {:added "0.2.0"}
  []
  (add-hook #'clojure.tools.nrepl.server/start-server #'require-java-functions))

(defn run-configuration
  "Initialize and configure Ultra's various components."
  {:added "0.1.0"}
  [{:keys [java repl stacktraces tests] :as opts}]
  (colorscheme/set-colorscheme opts)
  (if (not (false? repl))
    (repl/configure-repl! repl stacktraces))
  (if (not (false? stacktraces))
    (stacktrace/configure-stacktraces!))
  (if (not (false? tests))
    (test/activate!))
  (if (not (false? java))
    (java-hooks)))

(defn configure!
  "Only run the configuration step once."
  {:added "0.1.0"}
  [opts]
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration opts))))
