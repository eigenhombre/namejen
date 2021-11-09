(ns namejen.dep
  (:require [namejen.cross :as cross]))

(defn g [] (- 5 (cross/h 3)))
