(ns ultra.util)

(defn without-meta
  "If the object contains metadata, remove it."
  [x]
  (if (meta x)
    (with-meta x nil)
    x))
