(ns namejen.core
  (:require [namejen.names :refer [name-maker]])
  (:gen-class))

(defn -main [& [nstr & _]]
  (/ 1 0)
  (let [n (if nstr (Integer/parseInt nstr) 50)]
    (->> name-maker
         repeatedly
         (take n)
         (clojure.string/join "\n")
         println)))
