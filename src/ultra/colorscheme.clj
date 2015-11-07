(ns ultra.colorscheme
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [io.aviso.exception]
            [puget.color.ansi :as ansi]
            [whidbey.repl]))

(defn valid-colorscheme?
  [k]
  (#{:solarized_dark
    :default} k))

(defn valid-ansi-color?
  [k]
  (#{:black :red :green :yellow :blue :magenta :cyan :white} k))

(defn load-colorscheme
  "Either load the provided colorscheme, or use the default."
  [colorscheme]
  (let [colorscheme (or (valid-colorscheme? colorscheme) :default)]
    (edn/read-string
      (slurp
        (io/resource
          (format "colorschemes/%s.edn" (name colorscheme)))))))

(defn ansi-vec->pretty-str
  "Convert a Puget-style ANSI vec to a Pretty font string. If nil, remove
  all formatting."
  [v]
  (if-let [k (first (filter valid-ansi-color? v))]
    (eval (symbol "io.aviso.ansi" (str (name k) "-font")))
    (ansi/escape :none)))

(defn set-pretty-colors
  "Set the color palette for pretty (which handles exceptions)."
  [color-scheme]
  (alter-var-root
    #'io.aviso.exception/*fonts*
    merge
    {:clojure-frame (ansi-vec->pretty-str
                     (:symbol color-scheme))
     :exception (ansi-vec->pretty-str
                 (:exception color-scheme))
     :function-name (ansi-vec->pretty-str
                     (:function-symbol color-scheme))
     :java-frame (ansi-vec->pretty-str
                   (:symbol color-scheme))
     :message (ansi-vec->pretty-str nil)
     :omitted-frame (ansi-vec->pretty-str
                      (:symbol color-scheme))
     :property (ansi-vec->pretty-str
                 (:number color-scheme))
     :reset (ansi-vec->pretty-str nil)
     :source (ansi-vec->pretty-str
               (:string color-scheme))}))

(defn set-colorscheme
  [{:keys [color-scheme] :as opts}]
  (let [color-scheme (if (map? color-scheme)
                     color-scheme
                     (load-colorscheme color-scheme))]
    (alter-var-root
      #'whidbey.repl/printer
      merge
      {:color-scheme color-scheme})
    (set-pretty-colors color-scheme)))
