(ns ultra.hardcore
  "See what I did there?"
  (:require [ultra.colorscheme :as colorscheme]
            [ultra.repl :as repl]
            [ultra.stacktrace :as stacktrace]
            [ultra.test :as test]))

(def configured? (atom {}))

(defn run-configuration
  "Initialize and configure Ultra's various components."
  {:added "0.1.0"}
  [{:keys [repl stacktraces tests] :as opts}]
  (colorscheme/set-colorscheme opts)
  (if (not (false? repl))
    (repl/configure-repl! repl stacktraces))
  (if (not (false? stacktraces))
    (stacktrace/configure-stacktraces!))
  #_(if (not (false? tests))
    (test/activate!)))

(defn configure!
  "Only run the configuration step once."
  {:added "0.1.0"}
  [opts]
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration opts))))
