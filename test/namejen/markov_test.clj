(ns namejen.markov-test
  (:require [midje.sweet :refer :all]
            [namejen.markov :refer [generate-single-name]]))


(facts "I can generate a name with the basic name maker"
  (generate-single-name) =not=> ""
  (empty? (generate-single-name)) => falsey)
