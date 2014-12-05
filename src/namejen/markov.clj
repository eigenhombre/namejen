(ns namejen.markov)


(defn add-to-chain [chainlen chainmap chain]
  (let [key (take chainlen chain)
        nxt (nth chain chainlen)
        lookup (chainmap key)
        curvec (if lookup lookup #{})]
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
  (make-nextmap chainlen (->> input-sequences
                              (map vec)
                              (map #(conj % 'STOP-STATE))
                              (map #(partition (inc chainlen) 1 %))
                              (apply concat))))


(defn build-map-from-strings [chainlen strings]
  (->> strings
       (map clojure.string/lower-case)
       (build-map-from-seqs chainlen)))


(defn get-name-data [f]
  (->> f
       clojure.java.io/resource
       slurp
       clojure.string/split-lines))


(defn generate-sequence
  "
  Generate a sequence using Markov chain by following choices captured in
  'nextmap'.  Start with an initial sequence/key randomly chosen from
  the keys in the map.  Select next letter randomly based on the
  available ones for that key.  Collect all letters this way, adding
  them and changing the current key until STOP-STATE symbol is reached.
  "
  [nextmap]
  (loop [current (rand-nth (keys nextmap))
         all current
         next (rand-nth (vec (nextmap current)))]
    (if (= next 'STOP-STATE)
      all
      (let [next-current (concat (rest current) [next])
            next-all (concat all [next])
            next-next (rand-nth (vec (nextmap next-current)))]
        (recur next-current next-all next-next)))))


(defn generate-single-name
  "
  For a sequence of letters, captialize and return as string.
  "
  [nextmap]
  (->> (generate-sequence nextmap)
       (apply str)
       clojure.string/capitalize))
