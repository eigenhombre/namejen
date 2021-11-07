(ns namejen.main
  (:require [clojure.string :as string]
            [namejen.names :refer [name-maker]])
  (:gen-class))

(defn -main
  "
  Print `nstr` (default 50) person names to `stdout`.
  "
  {:doc/format :markdown}
  [& [nstr & _]]
  (let [n (if nstr (Integer/parseInt nstr) 50)]
    (->> name-maker
         repeatedly
         (take n)
         (string/join "\n")
         println)))
