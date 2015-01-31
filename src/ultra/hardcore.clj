(ns ultra.hardcore
  "See what I did there?"
  (:require [ultra.colorscheme :as colorscheme]
            [ultra.ns :as ns]
            [ultra.reflect :as reflect]
            [ultra.repl :as repl]
            [ultra.stacktrace :as stacktrace]
            [ultra.test :as test]))

(def configured? (atom {}))

(defn run-configuration
  "Initialize and configure Ultra's various components."
  {:added "0.1.0"}
  [{:keys [java quiet-lint repl stacktraces tests] :as opts}]
  (colorscheme/set-colorscheme opts)
  (if (not (false? repl))
    (repl/configure-repl! repl stacktraces))
  (if (not (false? stacktraces))
    (stacktrace/configure-stacktraces!))
  #_(if (not (false? tests))
    (test/activate!))
  (if (not (false? quiet-lint))
    (ns/load!))
  (if (not (false? java))
    (reflect/inject-java-functions)))

(defn configure!
  "Only run the configuration step once."
  {:added "0.1.0"}
  [opts]
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration opts))))
