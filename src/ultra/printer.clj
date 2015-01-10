(ns ultra.printer
  (:require [puget.printer :as printer]
            [whidbey.render :as render]))

(defn cprint
  [x]
  (printer/cprint x render/puget-options))
