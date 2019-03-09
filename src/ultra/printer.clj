(ns ultra.printer
  (:require [puget.printer :as printer]
            [ultra.colorscheme :as colorscheme]
            [whidbey.repl :as repl]))

(defn cprint
  "Puget's cprint, set to always use the Puget options."
  {:added "0.1.3"}
  ([x]
   (cprint x :default))
  ([x colorscheme]
   (printer/cprint x (printer/pretty-printer
                       {:color-scheme (colorscheme/load-colorscheme colorscheme)}))))
