(ns ultra.repl
  (:require [clojure.main :as main]
            [clojure.repl :as repl]
            [clojure.tools.nrepl.server]
            [glow.terminal]
            [glow.colorschemes]
            [glow.parse]
            [io.aviso.repl :as pretty-repl]
            [ultra.printer :refer [cprint]]))

(defmacro source
  "Prints the source code for the given symbol, if it can find it.
  This requires that the symbol resolve to a Var defined in a
  namespace for which the .clj is in the classpath.

  Example: (source filter)"
  {:added "0.2.1"}
  [n]
  `(if-let [source# (clojure.repl/source-fn '~n)]
     (println (glow.terminal/ansi-colorize
                glow.colorschemes/terminal-default
                (glow.parse/parse source#)))
     (println "Source not found")))

(defn replace-source
  "First, re-define `clojure.repl/source (which is a macro) to be false.
  Then, install our new preferred macro in its place.

  Note: I'm happy with how this works, but not the code itself. Odds are good
  that I'll try to refactor this in the future."
  {:added "0.3.5"}
  []
  (binding [*ns* (the-ns 'clojure.repl)]
    (require 'glow.terminal)
    (require 'glow.colorschemes)
    (require 'glow.parse)
    (eval (read-string (repl/source-fn 'ultra.repl/source)))))

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
   (constantly pretty-repl/pretty-repl-caught))
  (alter-var-root
   #'repl/pst
   (constantly pretty-repl/pretty-pst)))

(defn configure-repl!
  "Was the fn name not clear enough?"
  {:added "0.1.0"}
  [repl stacktraces]
  (when (not (false? repl))
    (require 'ultra.repl.whidbey)
    (require 'whidbey.repl)
    (eval '(ultra.repl.whidbey/add-whidbey-middleware))
    (eval `(whidbey.repl/update-options! ~repl))
    (replace-source))
  (when (not (false? stacktraces))
    (add-pretty-middleware)))
