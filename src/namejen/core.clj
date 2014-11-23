(ns namejen.core
  (:require [namejen.fun :refer [funny-name-maker]])
  (:gen-class))


(defn -main [& [nstr & _]]
  (let [n (if nstr (Integer/parseInt nstr) 50)]
    (->> (funny-name-maker)
         (take n)
         (clojure.string/join "\n")
         println)))
