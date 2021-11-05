(ns namejen.markov-test
  (:require [clojure.test :refer [deftest is testing]]
            [namejen.io :refer [get-name-data]]
            [namejen.markov :refer [generate-single-name
                                    make-nextmap
                                    build-map-from-strings
                                    build-map-from-seqs]]))

(deftest name-generation-basics
  (testing "I can generate a name with the basic name maker"
    (let [nom (->> "names.txt"
                   get-name-data
                   (build-map-from-strings 4)
                   generate-single-name)]
      (is (seq nom)))))

(deftest chain-test
  (testing "making chains"
    (is (= '{(a l f) #{r i}}
           (make-nextmap 3 '((a l f r) (a l f i)))))
    (is (= '{(a l) #{f}}
           (make-nextmap 2 '((a l f r) (a l f i)))))
    (is (= '{(a) #{l}}
           (make-nextmap 1 '((a l f r) (a l f i)))))
    (is (= '{(a) #{l}
             (b) #{l}}
           (make-nextmap 1 '((a l) (b l)))))))

(deftest trransition-map-test
  (testing "building Markov transition map from seqs"
    (is (= {["A" "dog"] #{"is"}
            ["dog" "is"] #{"not" "hungry"}
            ["is" "hungry"] #{'STOP-STATE}
            ["is" "not"] #{"a"}
            ["not" "a"] #{"cat"}
            ["a" "cat"] #{'STOP-STATE}}
           (build-map-from-seqs 2 [["A" "dog" "is" "not" "a" "cat"]
                                   ["A" "dog" "is" "hungry"]])))))
