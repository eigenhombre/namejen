(ns namejen.io
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn resource-file-lines
  "
  Read a file from a project resource, and return a collection of
  lines.
  "
  {:doc/format :markdown}
  [f]
  (->> f
       io/resource
       slurp
       string/split-lines))
