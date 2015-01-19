(ns ultra.hardcore
  "See what I did there?"
  (:require [ultra.colorscheme :as colorscheme]
            [ultra.reflect :as reflect]
            [ultra.repl :as repl]
            [ultra.stacktrace :as stacktrace]
            [ultra.test :as test]))

(def configured? (atom {}))

(defn run-configuration
  "Initialize and configure Ultra's various components."
  {:added "0.1.0"}
  [opts]
  (colorscheme/set-colorscheme opts)
  (repl/configure-repl!)
  (stacktrace/configure-stacktraces!)
  (test/activate!)
  (reflect/inject-java-functions))

(defn configure!
  "Only run the configuration step once."
  {:added "0.1.0"}
  [opts]
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration opts))))
