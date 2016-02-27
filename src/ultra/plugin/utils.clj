(ns ultra.plugin.utils
  "Convenience functions for Leiningen plugins.

   Credit to @greglook for most of these."
  (:require [clj-semver.core :as semver]))

(defn remove-plugin
  [project plugin]
  (assoc project :plugins
         (into [] (remove #(= (first %) plugin) (:plugins project)))))

(defn clojure-dep?
  "Returns the specific dependency entry for Clojure? (org.clojure/clojure)"
  {:added "0.4.1"}
  [depvec]
  (when (= (first depvec) 'org.clojure/clojure)
    depvec))

(defn supports-cljc?
  "Does this project support reader conditions (.cljc files)?"
  {:added "0.4.1"}
  [project]
  (not (semver/older? (second (some clojure-dep? (:dependencies project))) "1.7.0")))

(defn- find-plugin-version
  "Looks up the plugins in the project map and tries to find the version
  specified for the given symbol. Returns nil if none matches."
  {:added "0.3.3"}
  [project plugin]
  (try
    (some (fn [[p v]] (when (= p plugin) v))
          (:plugins project))
    (catch Exception e
      nil)))

(defn plugin-dependency
  "Looks up the version for the given plugin, or sets it to `\"RELEASE\"`.
  Returns a dependency vector entry."
  {:added "0.3.3"}
  [project plugin]
  (vector
   plugin
   (or (find-plugin-version project plugin)
       "RELEASE")))

(defn add-dependencies
  "Adds some dependencies to the end of the current vector."
  {:added "0.3.3"}
  [project & deps]
  (update-in project [:dependencies] concat deps))

(defn add-repl-init
  "Adds the given repl initialization forms to the `:init` key in
  `:repl-options`. Any existing initialization will happen before the
  form is executed."
  {:added "0.3.3"}
  [project & forms]
  (update-in project
             [:repl-options :init]
             (fn [current]
               (if current
                 `(do ~current ~@forms)
                 `(do ~@forms)))))

(defn add-nrepl-middleware
  "Adds the middleware identified by the given symbol to the *front* of the
  current nrepl-middleware list in the project map."
  {:added "0.3.3"}
  [project sym]
  (update-in project
             [:repl-options :nrepl-middleware]
             #(into [sym] %)))

(defn set-interactive-eval-renderer
  "Sets the nrepl renderer for the interactive-eval context to the function
  named by the given symbol."
  {:added "0.3.3"}
  [project sym]
  (assoc-in project
            [:repl-options :nrepl-context :interactive-eval :renderer]
            sym))
