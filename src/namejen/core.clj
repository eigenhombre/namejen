(ns namejen.core
  (:use [clojure.string :only (split-lines lower-case capitalize join)]
        [expectations])
  (:import (java.io InputStreamReader BufferedReader))
  (:import (javax.script ScriptEngineManager ScriptEngine))
  (:gen-class))

(expectations/disable-run-on-shutdown)

(defn make-nextmap [chainlen parts]
  "Make a map of next values in a Markov chain, given a vector of
   input sequences with length==chainlen+1, where the first <chainlen>
   values of each are the keys, and the values are a vector of the
   last letter seen following that sequence.  For example,
   ((a l f r) (a l f i)) -> {(a l f) [r i]}"
  (loop [parts parts
         ret {}]
    (let [chain (first parts)
          key (take chainlen chain)
          next (nth chain chainlen)
          lookup (ret key)
          curvec (if lookup lookup [])]
      (if chain
        (recur (rest parts)
               (assoc ret key (conj curvec next)))
        ret))))

(defn generate-single-name [nextmap]
  "Generate a name using Markov chain by following choices captured in
   'nextmap'.  Start with an initial sequence/key randomly chosen from
   the keys in the map.  Select next letter randomly based on the
   available ones for that key.  Collect all letters this way, adding
   them and changing the current key until \newline is reached."
  (loop [current (rand-nth (keys nextmap))
         all current
         next (rand-nth (nextmap current))]
    (if (= next \newline)
      (capitalize (apply str all))
      (let [next-current (concat (rest current) [next])
            next-all (concat all [next])
            next-next (rand-nth (nextmap next-current))]
        (recur next-current next-all next-next)))))


(defn prefix [] (rand-nth ["Dr." "Mr." "Ms." "Mrs." "M." "Miss" "Sr." "Herr" "Mme." "Sir"]))
(defn generation [] (rand-nth ["Sr." "Jr." "Jr." "I" "II" "III" "III" "IV" "V"]))
(def suffixes ["Esq." "Ph.D." "LCPT" "MD" "LMA" "LMT"])
(defn suffix [] (rand-nth ["Esq." "R.N." "Ph.D." "LCPT" "MD" "LMA" "LMT"]))
(defn num-names [] (rand-nth [1 2 2 2 2 2 2 2 2 2 2 3 3 5]))
(defn choose-whether [n] (= (rand-int n) 0))


(defn in? [x l] (if (nil? (some #{x} l)) false true))

(expect (in? 0 [0 1 2]) true)
(expect (in? 3 [0 1 2]) false)


(defn take-n [n l]
  (loop [ret [], n n]
    (if (= n 0)
      ret
      (let [choice (rand-nth l)]
        (if (in? choice ret)
          (recur ret n)
          (recur (conj ret choice) (dec n)))))))

(expect (set (take-n 3 [:a :b :c])) #{:a :b :c})
(expect (in? (first (take-n 1 [:a :b :c])) [:a :b :c]) true)
(expect (in? (first (take-n 1 [:a :b :c])) [:d :e :f]) false)


(defn name-maker [chainlen namefile]
  (let [allnames (map (comp #(str % \newline) lower-case)
                   (split-lines
                    (slurp namefile)))
        parts (apply concat (map #(partition (inc chainlen) 1 %) allnames))
        nextmap (make-nextmap chainlen parts)]
    (fn []
      (let [names (join " "                                             ;; Core names
                       (repeatedly (num-names)
                                   #(generate-single-name nextmap)))
            with-title (if (choose-whether 5)                           ;; Add title (Ms., Dr., ...)
                         (str (prefix) " " names)
                         names)
            with-gen (if (choose-whether 5)                             ;; Add Jr., III, etc.
                    (str with-title " " (generation))
                    with-title)
            suffixes (set (take-n (rand-int (rand-int 3)) suffixes))    ;; Add Ph.D., ...
            with-suffixes (if (empty? suffixes)
                            with-gen
                            (str with-gen ", " (join ", " suffixes)))]
        with-suffixes))))


(defn get_resource [nm]
  "See http://stackoverflow.com/questions/2044394/how-to-load-program-resources-in-clojure"
  (ClassLoader/getSystemResource nm))


(defn print-lines [l] (doseq [i l] (println i)))


(defn -main [& args]
  (let [f (first args)
        n (if (nil? f) 50 (. Integer parseInt f))]
    (print-lines
     (repeatedly n
                 (name-maker 4 (get_resource "names.txt"))))))
