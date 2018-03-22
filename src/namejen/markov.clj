(ns namejen.markov
  (:require [namejen.io :refer [get-name-data]]))


(defn add-to-chain [chainlen chainmap chain]
  (let [key (take chainlen chain)
        nxt (nth chain chainlen)
        lookup (chainmap key)
        curvec (or lookup #{})]
    (assoc chainmap key (conj curvec nxt))))


(defn make-nextmap
  "
  Make a map of next values in a Markov chain, given a vector of
  input sequences with length == chainlen+1, where the first
  `chainlen` values of each are the keys, and the values are a vector
  of the last letter seen following that sequence.  For example,

  `((a l f r) (a l f i)) --> {(a l f) [r i]}`
  "
  [chainlen parts]
  (reduce (partial add-to-chain chainlen) {} parts))


(defn build-map-from-seqs [chainlen input-sequences]
  (->> input-sequences
       (map vec)
       (map #(conj % 'STOP-STATE))
       (map #(partition (inc chainlen) 1 %))
       (apply concat)
       (make-nextmap chainlen)))


(defn build-map-from-strings [chainlen strings]
  (->> strings
       (map clojure.string/lower-case)
       (build-map-from-seqs chainlen)))


(defn generate-sequence
  "
  Generate a sequence using Markov chain by following choices captured
  in 'nextmap'.  Start with an initial sequence/key randomly chosen
  from the keys in the map.  Select next letter randomly based on the
  available ones for that key.  Collect all letters this way, adding
  them and changing the current key until STOP-STATE symbol is
  reached, or there are no available choices for the current position
  in the chain.
  "
  [nextmap]
  (loop [current (rand-nth (keys nextmap))
         all current
         current-value (rand-nth (vec (nextmap current)))]
    (when current-value
      (if (= current-value 'STOP-STATE)
        all
        (let [next-current (concat (rest current) [current-value])
              next-all (concat all [current-value])
              nextvals (vec (nextmap next-current))]
          (when (seq nextvals)
            (recur next-current next-all (rand-nth nextvals))))))))


(defn generate-single-name
  "
  For a sequence of letters, captialize and return as string.
  "
  [nextmap]
  (->> (generate-sequence nextmap)
       (apply str)
       clojure.string/capitalize))
