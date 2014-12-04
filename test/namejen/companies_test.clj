(ns namejen.companies-test
  (:require [namejen.companies :refer :all]
            [midje.sweet :refer :all]))


(fact "Smoke test for company generation"
  (->> gen-company-name-and-website
       (repeatedly 20)
       (apply concat)
       count) => 40)
