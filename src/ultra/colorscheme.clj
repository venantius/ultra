(ns ultra.colorscheme
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [whidbey.render]))

(def VALID_COLORSCHEMES
  #{:solarized_dark
    :default})

(defn load-colorscheme
  "Either load the provided colorscheme, or use the default."
  [colorscheme]
  (let [colorscheme (or (VALID_COLORSCHEMES colorscheme) :default)]
    (edn/read-string
      (slurp
        (io/file
          (io/resource
            (format "colorschemes/%s.edn" (name colorscheme))))))))

(defn set-colorscheme
  [{:keys [color-scheme] :as opts}]
  (let [color-scheme (if (map? color-scheme)
                     color-scheme
                     (load-colorscheme color-scheme))]
    (alter-var-root
      #'whidbey.render/puget-options
      merge
      (assoc opts :color-scheme color-scheme))))
