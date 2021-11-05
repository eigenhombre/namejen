(ns namejen.names-test
  (:require [clojure.test :refer [deftest testing is]]
            [namejen.names :refer [name-maker]]))

(deftest name-maker-test
  (testing "default / main function makes a bunch of values"
    (is (every? (comp (complement zero?) count)
                (repeatedly 100 name-maker)))))
