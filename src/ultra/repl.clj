(ns ultra.repl
  (:require [clojure.main :as main]
            [clojure.repl :as repl]
            [glow.terminal]
            [glow.colorschemes]
            [glow.parse]
            [io.aviso.repl :as pretty-repl]
            [puget.color.ansi :as ansi]
            [ultra.printer :refer [cprint]]))

;; Compatibility with the legacy tools.nrepl and the new nREPL 0.4.x.
;; The assumption is that if someone is using old lein repl or boot repl
;; they'll end up using the tools.nrepl, otherwise the modern one.
(if (find-ns 'clojure.tools.nrepl)
  (require
   '[clojure.tools.nrepl.server :as nrepl-server])
  (require
   '[nrepl.server :as nrepl-server]))

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

(def doc-colorscheme
  (assoc glow.colorschemes/terminal-default
         :variable :default
         :core-fn :default))

(defn syntax-println
  "Print the input data structure as a syntax-highlighted string."
  [arglists]
  (println
   (glow.terminal/ansi-colorize
    doc-colorscheme
    (glow.parse/parse (str arglists)))))

(defn print-doc
  "Replaces clojure.repl/print-doc."
  [{:keys [arglists doc forms ns name url] :as m}]
  (println "-------------------------")
  (println (str (when ns (str (ns-name ns) "/")) name))
  (cond
    forms (doseq [f forms]
            (print "  ")
            (syntax-println f))
    arglists (syntax-println arglists))
  (if (:special-form m)
    (do
      (println "Special Form")
      (println " " doc)
      (if (contains? m :url)
        (when url
          (println (str "\n  Please see http://clojure.org/" url)))
        (println (str "\n  Please see http://clojure.org/special_forms#"
                      name))))
    (do
      (when (:macro m)
        (println "Macro"))
      (println " " doc))))

(defn replace-doc
  "Replace `print-doc` in `clojure.repl` to syntax-highlight arglists."
  {:added "0.5.3"}
  []
  (alter-var-root #'clojure.repl/print-doc (constantly ultra.repl/print-doc)))

(defn add-middleware
  "Alter the default handler to include the provided middleware."
  {:added "0.1.0"}
  [middleware]
  ;; Compatibility with the legacy tools.nrepl and the new nREPL 0.4.x.
  ;; The assumption is that if someone is using old lein repl or boot repl
  ;; they'll end up using the tools.nrepl, otherwise the modern one.
  (if (find-ns 'clojure.tools.nrepl)
    (alter-var-root
     #'clojure.tools.nrepl.server/default-handler
     partial
     middleware)
    (alter-var-root
     #'nrepl.server/default-handler
     partial
     middleware)))

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
    (replace-source)
    (replace-doc))
  (when (not (false? stacktraces))
    (add-pretty-middleware)))
