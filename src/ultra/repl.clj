(ns ultra.repl
  (:require [clojure.main :as main]
            [clojure.repl :as repl]
            [clojure.tools.nrepl.server]
            [io.aviso.repl :as pretty-repl]
            [ultra.printer :refer [cprint]]
            [ultra.printer.escape-hatch]))

(defmacro pprint-source
  "Prints the source code for the given symbol, if it can find it.
  This requires that the symbol resolve to a Var defined in a
  namespace for which the .clj is in the classpath.

  Example: (source filter)

  Note that unlike clojure.repl/source, this fn can execute code, and
  as such should only be used with trusted sources."
  {:added "0.2.1"}
  [n]
  `(cprint (read-string (or (clojure.repl/source-fn '~n)
                            (str "Source not found")))))

(defn add-middleware
  "Alter the default handler to include the provided middleware"
  {:added "0.1.0"}
  [middleware]
  (alter-var-root
   #'clojure.tools.nrepl.server/default-handler
   partial
   middleware))

(defn add-pretty-middleware
  "Add Aviso's Pretty functionality"
  {:added "0.1.2"}
  []
  (alter-var-root
   #'main/repl-caught
   (constantly pretty-repl/pretty-pst))
  (alter-var-root
   #'repl/pst
   (constantly pretty-repl/pretty-pst)))

(defn configure-repl!
  "Was the fn name not clear enough?"
  {:added "0.1.0"}
  [repl stacktraces]
  (when (not (false? repl))
    (require 'ultra.repl.whidbey)
    (eval '(ultra.repl.whidbey/add-whidbey-middleware)))
  (when (not (false? stacktraces))
    (add-pretty-middleware)))

