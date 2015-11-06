(ns ultra.printer.escape-hatch
  "Hooks into `puget.printer` that are intended as an escape hatch for
   custom data types."
  #_(:require [puget.data :as data]
            [puget.printer :refer [format-doc]])
  )

#_(def escaped-classes
  #{"class datomic.db.Db"})

#_(intern
  'puget.printer
  'formatter-dispatch
  (fn [value]
    (cond
      (satisfies? data/ExtendedNotation value) ::tagged-literal
      (escaped-classes (str (class value))) :default
      :else (type value))))
