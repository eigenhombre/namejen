(ns namejen.names-test
  (:require #?(:clj [clojure.test :refer [deftest is testing]])
            #?(:cljs [cljs.test :refer-macros [deftest testing is]])
            [namejen.names :refer [name-maker]]))

(deftest name-maker-test
  (testing "default / main function makes a bunch of values"
    (is (every? (comp (complement zero?) count)
                (repeatedly 100 name-maker)))))
