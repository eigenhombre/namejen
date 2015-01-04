(ns namejen.names-test
  (:require [midje.sweet :refer :all]
            [namejen.names :refer [name-maker]]))


(facts "default / main function makes a bunch of values"
  (let [nom (name-maker)]
    (> (count nom) 0) => true))
