(ns namejen.core
  (:use [clojure.string :only (split-lines lower-case capitalize join)]
        [expectations])
  (:import (java.io InputStreamReader BufferedReader))
  (:import (javax.script ScriptEngineManager ScriptEngine))
  (:gen-class))

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
 
(defn prefix [] (rand-nth ["Dr." "Mr." "Ms." "Mrs." "M." "Miss" "Sr." "Herr" "Mme."]))
(defn generation [] (rand-nth ["Sir" "Sr." "Jr." "Jr." "I" "II" "III" "III" "IV" "V"]))
(def suffixes ["Esq." "Ph.D." "LCPT" "MD" "LMA" "LMT"])
(defn suffix [] (rand-nth ["Esq." "Ph.D." "LCPT" "MD" "LMA" "LMT"]))
(defn num-names [] (rand-nth [1 2 2 2 2 2 2 2 2 2 2 3 3 5]))
(defn choose-whether [n] (= (rand-int n) 0))

(defn memberp [x l] (if ((set l) x) true false))  ; FIXME: remember proper function

(defn take-n [n l]
  (loop [ret [], n n]
    (if (= n 0)
      ret
      (let [choice (rand-nth l)]
        (if (memberp choice ret)
          (recur ret n)
          (recur (conj ret choice) (dec n)))))))

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
            w-title (if (choose-whether 5)                              ;; Add title (Ms., Dr., ...)
                      (str (prefix) " " names)
                      names)
            w-gen (if (choose-whether 5)                                ;; Add Jr., III, etc.
                    (str w-title " " (generation))
                    w-title)
            suffixes (set (take-n (rand-int (rand-int 3)) suffixes))    ;; Add Ph.D., ...
            with-suffixes (if (empty? suffixes)
                            w-gen
                            (str w-gen ", " (join ", " suffixes)))]
        with-suffixes))))


(defn print-lines [l] (doseq [i l] (println i)))

(defn get_resource [nm]
  "See http://stackoverflow.com/questions/2044394/how-to-load-program-resources-in-clojure"
  (ClassLoader/getSystemResource nm))

(defn -main []
  (print-lines
   (repeatedly 500
               (name-maker 4 (get_resource "names.txt")))))
                           ; (.getFile (clojure.java.io/resource "names.txt"))))))
                           ; was "/Users/jacobsen/Programming/Games/markovnamer/namefiles/all.txt"

(expect (memberp 0 [0 1 2]) true)
(expect (memberp 3 [0 1 2]) false)
(expect (memberp (first (take-n 1 [:a :b :c])) [:a :b :c]) true)
(expect (memberp (first (take-n 1 [:a :b :c])) [:d :e :f]) false)
(expect (set (take-n 3 [:a :b :c])) #{:a :b :c})

