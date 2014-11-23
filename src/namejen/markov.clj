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


(defn map-for-name-data [chainlen name-data]
  (let [lc-names (map (comp vec clojure.string/lower-case) name-data)
        allnames (map #(conj % 'STOP-STATE) lc-names)
        parts (apply concat (map #(partition (inc chainlen) 1 %) allnames))]
    (make-nextmap chainlen parts)))


(defn get-default-name-data []
  (->> "names.txt"
       clojure.java.io/resource
       slurp
       clojure.string/split-lines))


(def default-nextmap
  (map-for-name-data 4 (get-default-name-data)))


(defn generate-single-name
  "
  Generate a name using Markov chain by following choices captured in
  'nextmap'.  Start with an initial sequence/key randomly chosen from
  the keys in the map.  Select next letter randomly based on the
  available ones for that key.  Collect all letters this way, adding
  them and changing the current key until \newline is reached.
  "
  ([]
   (generate-single-name default-nextmap))
  ([nextmap]
   (loop [current (rand-nth (keys nextmap))
          all current
          next (rand-nth (vec (nextmap current)))]
     (if (= next 'STOP-STATE)
       (clojure.string/capitalize (apply str all))
       (let [next-current (concat (rest current) [next])
             next-all (concat all [next])
             next-next (rand-nth (vec (nextmap next-current)))]
         (recur next-current next-all next-next))))))
