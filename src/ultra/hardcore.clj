(ns ultra.hardcore
  "See what I did there?"
  (:require [ultra.colorscheme :as colorscheme]
            [robert.hooke :refer [add-hook]]))

;; For reasons that aren't totally clear to me, we need this import to find clojure.test?
(if (find-ns 'clojure.tools.nrepl)
  (require
   '[clojure.tools.nrepl.server :as nrepl-server])
  (require
   '[nrepl.server :as nrepl-server]))

(def configured? (atom {}))

(defmacro configure-repl!
  "Dynamically import ultra's repl namespace and configure the REPL."
  {:added "0.3.0"}
  [repl stacktraces]
  `(do ~(require 'ultra.repl)
       (ultra.repl/configure-repl! ~repl ~stacktraces)))

(defmacro configure-stacktraces!
  "Dynamically import ultra's stacktrace namespace and configure them."
  {:added "0.3.0"}
  []
  `(do ~(require 'ultra.stacktrace)
       (ultra.stacktrace/configure-stacktraces!)))

(defmacro configure-tests!
  "Dyanmically import ultra's test namespace and configure them."
  {:added "0.3.0"}
  []
  `(do ~(require 'ultra.test)
       (ultra.test/activate!)))

(defmacro set-colorscheme!
  "Dynamically import ultra's colorscheme namespace and configures it."
  {:added "0.3.0"}
  [opts]
  `(colorscheme/set-colorscheme ~opts))

(defn run-configuration
  "Initialize and configure Ultra's various components."
  {:added "0.1.0"}
  [{:keys [repl stacktraces tests] :as opts}]
  (when (or (not (false? repl))
            (not (false? stacktraces))
            (not (false? tests)))
    (eval `(set-colorscheme! ~opts)))
  (when (not (false? repl))
    (eval `(configure-repl! ~repl ~stacktraces)))
  (when (not (false? stacktraces))
    (eval `(configure-stacktraces!)))
  (when (not (false? tests))
    (eval `(configure-tests!))))

(defn configure!
  "Only run the configuration step once."
  {:added "0.1.0"}
  [opts]
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration opts))))

(defn add-test-hooks!
  "Add hooks to configure tests"
  {:added "0.3.1"}
  [opts]
  (add-hook #'clojure.test/try-expr
            (fn [f & args]
              (configure! (assoc opts :repl false))
              (apply f args))))
