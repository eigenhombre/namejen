(ns namejen.io)


(defn get-name-data [f]
  (->> f
       clojure.java.io/resource
       slurp
       clojure.string/split-lines))
