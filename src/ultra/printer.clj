(ns ultra.printer
  (:require [puget.printer :as printer]
            [whidbey.render :as render]))

(defn cprint
  "Puget's cprint, set to always use the Puget options."
  {:added "0.1.3"}
  [x]
  (printer/cprint x render/puget-options))
