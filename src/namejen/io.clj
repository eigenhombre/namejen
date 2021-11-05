(ns namejen.io
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn get-name-data [f]
  (->> f
       io/resource
       slurp
       string/split-lines))
