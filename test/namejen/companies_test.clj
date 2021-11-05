(ns namejen.companies-test
  (:require [clojure.test :refer [deftest testing is]]
            [namejen.companies :refer [gen-company-name-and-website]]))

(deftest companies-test
  (testing "company generation"
    (dotimes [_ 100]
      (let [[compname website] (gen-company-name-and-website)]
        (testing "Name is nonempty"
          (is (seq compname)))
        (testing "Website ends in .xy, .xyz, .pdqr, ..."
          (is (re-find #"[^\.]+\.[^\.]{2,4}$" website)))))))
