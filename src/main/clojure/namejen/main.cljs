(ns namejen.main
  (:require [namejen.dep :as d]))

(defn f [x] (+ (d/g) 4))
