(ns namejen.fun
  (:require [namejen.markov :refer [generate-single-name
                                    get-name-data
                                    map-for-name-data]]))


(defn prefix [] (rand-nth ["Dr." "Mr." "Ms." "Mrs." "M."
                           "Miss" "Sr." "Herr" "Mme." "Sir"]))

(defn generation [] (rand-nth ["Sr." "Jr." "Jr."
                               "I" "II" "III" "III" "IV" "V"]))

(def suffixes ["Esq." "Ph.D." "LCPT" "MD" "LMA" "LMT"])

(defn suffix [] (rand-nth ["Esq." "R.N." "Ph.D." "LCPT" "MD" "LMA" "LMT"]))

(defn choose-whether [n] (= (rand-int n) 0))

(defn in? [x l] (not (nil? (some #{x} l))))

(defn num-names [] (rand-nth [1 2 2 2 2 2 2 2 2 2 2 3 3 5]))


(defn take-n [n l]
  (loop [ret [], n n]
    (if (= n 0)
      ret
      (let [choice (rand-nth l)]
        (if (in? choice ret)
          (recur ret n)
          (recur (conj ret choice) (dec n)))))))


(defn get-default-name-data []
  (get-name-data "names.txt"))


(def default-nextmap
  (map-for-name-data 4 (get-default-name-data)))


(defn funny-name-maker []
  (repeatedly
   (fn []
     (let [genname (partial generate-single-name default-nextmap)
           core-names (clojure.string/join " "
                              (repeatedly (num-names) genname))
           ;; Add title (Ms., Dr., ...):
           with-title (if (choose-whether 5)
                        (str (prefix) " " core-names)
                        core-names)
           ;; Add Jr., III, etc.:
           with-gen (if (choose-whether 5)
                      (str with-title " " (generation))
                      with-title)
           ;; Add Ph.D., ...:
           suffixes (set (take-n (rand-int (rand-int 3)) suffixes))]
       (if (empty? suffixes)
         with-gen
         (str with-gen ", " (clojure.string/join ", " suffixes)))))))
