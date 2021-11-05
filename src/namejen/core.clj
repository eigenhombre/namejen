(ns namejen.core
  (:require [clojure.string :as string]
            [namejen.names :refer [name-maker]])
  (:gen-class))

(defn -main [& [nstr & _]]
  (let [n (if nstr (Integer/parseInt nstr) 50)]
    (->> name-maker
         repeatedly
         (take n)
         (string/join "\n")
         println)))
