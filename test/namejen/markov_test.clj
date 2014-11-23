(ns namejen.markov-test
  (:require [midje.sweet :refer :all]
            [namejen.markov :refer [generate-single-name make-nextmap]]))


(facts "I can generate a name with the basic name maker"
  (let [nom (generate-single-name)]
    (empty? nom) => falsey))

(facts "About making chains"
  (make-nextmap 3 '((a l f r) (a l f i))) => '{(a l f) #{r i}}
  (make-nextmap 2 '((a l f r) (a l f i))) => '{(a l) #{f}}
  (make-nextmap 1 '((a l f r) (a l f i))) => '{(a) #{l}}
  (make-nextmap 1 '((a l) (b l))) => '{(a) #{l}
                                       (b) #{l}})
