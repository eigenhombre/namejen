(ns namejen.fun-test
  (:require [midje.sweet :refer :all]
            [namejen.fun :refer [funny-name-maker in? take-n]]))


(facts "about in"
  (in? 0 [0 1 2]) => true
  (in? 3 [0 1 2]) => false)


(facts "about take-n"
  (set (take-n 3 [:a :b :c])) => #{:a :b :c}
  (in? (first (take-n 1 [:a :b :c])) [:a :b :c]) => true
  (in? (first (take-n 1 [:a :b :c])) [:d :e :f]) => false)


(facts "default / main function makes a bunch of values"
  (count (take 10 (funny-name-maker))) => 10)
