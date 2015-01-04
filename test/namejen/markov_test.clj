(ns namejen.markov-test
  (:require [midje.sweet :refer :all]
            [namejen.markov :refer [generate-single-name
                                    make-nextmap
                                    build-map-from-strings
                                    build-map-from-seqs]]
            [namejen.io :refer [get-name-data]]))


(facts "I can generate a name with the basic name maker"
  (let [nom (->> "names.txt"
                 get-name-data
                 (build-map-from-strings 4)
                 generate-single-name)]
    (empty? nom) => falsey))


(facts "About making chains"
  (make-nextmap 3 '((a l f r) (a l f i))) => '{(a l f) #{r i}}
  (make-nextmap 2 '((a l f r) (a l f i))) => '{(a l) #{f}}
  (make-nextmap 1 '((a l f r) (a l f i))) => '{(a) #{l}}
  (make-nextmap 1 '((a l) (b l))) => '{(a) #{l}
                                       (b) #{l}})


(facts "About building Markov transition map from seqs"
  (build-map-from-seqs 2 [["A" "dog" "is" "not" "a" "cat"]
                          ["A" "dog" "is" "hungry"]])
  => {["A" "dog"] #{"is"}
      ["dog" "is"] #{"not" "hungry"}
      ["is" "hungry"] #{'STOP-STATE}
      ["is" "not"] #{"a"}
      ["not" "a"] #{"cat"}
      ["a" "cat"] #{'STOP-STATE}})
