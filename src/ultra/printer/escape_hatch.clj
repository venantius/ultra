(ns ultra.printer.escape-hatch
  "Hooks into `puget.printer` that are intended as an escape hatch for
   custom data types."
  (:require [puget.data :as data]
            [puget.printer :refer [format-doc]]))

(defmethod format-doc datomic.db.Db
  [value]
  ((get-method format-doc :default) value))

(prefer-method format-doc datomic.db.Db clojure.lang.IPersistentMap)
