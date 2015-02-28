(ns ultra.hardcore
  "See what I did there?"
  (:require [clojure.tools.nrepl.server]
            [robert.hooke :refer [add-hook]]))

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

(defmacro configure-repl!
  "Dynamically import ultra's repl namespace and configure the REPL."
  {:added "0.2.2"}
  [repl stacktraces]
  `(do ~(require 'ultra.repl)
       (ultra.repl/configure-repl! ~repl ~stacktraces)))

(defmacro configure-stacktraces!
  "Dynamically import ultra's stacktrace namespace and configure them."
  {:added "0.2.2"}
  []
  `(do ~(require 'ultra.stacktrace)
       (ultra.stacktrace/configure-stacktraces!)))

(defmacro configure-tests!
  "Dyanmically import ultra's test namespace and configure them."
  {:added "0.2.2"}
  []
  `(do ~(require 'ultra.test)
       (ultra.test/activate!)))

(defmacro set-colorscheme!
  "Dynamically import ultra's colorscheme namespace and configures it."
  {:added "0.2.2"}
  [opts]
  `(do ~(require 'ultra.colorscheme)
       (ultra.colorscheme/set-colorscheme ~opts)))

(defn run-configuration
  "Initialize and configure Ultra's various components."
  {:added "0.1.0"}
  [{:keys [java repl stacktraces tests] :as opts}]
  (when (and (not (false? repl))
             (not (false? stacktraces))
             (not (false? tests)))
    (eval `(set-colorscheme! ~opts)))
  (if (not (false? repl))
    (eval `(configure-repl! ~repl ~stacktraces)))
  (if (not (false? stacktraces))
    (eval `(configure-stacktraces!)))
  (if (not (false? tests))
    (eval `(configure-tests!)))
  (if (not (false? java))
    (java-hooks)))

(defn configure!
  "Only run the configuration step once."
  {:added "0.1.0"}
  [opts]
  (if (empty? @configured?)
    (swap! configured? assoc :config (run-configuration opts))))
