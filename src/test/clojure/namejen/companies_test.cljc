(ns namejen.companies-test
  (:require #?(:clj [clojure.test :refer [deftest is testing]])
            #?(:cljs [cljs.test :refer-macros [deftest testing is]])
            [namejen.companies :refer [gen-company]]))

(deftest companies-test
  (testing "company generation"
    (dotimes [_ 100]
      (let [{compname :name, website :website} (gen-company)]
        (testing "Name is nonempty"
          (is (seq compname)))
        (testing "Website ends in .xy, .xyz, .pdqr, ..."
          (is (re-find #"[^\.]+\.[^\.]{2,4}$" website)))))))
