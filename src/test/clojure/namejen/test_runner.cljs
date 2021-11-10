(ns test.clojure.namejen.test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [namejen.markov-test]))

(enable-console-print!)

(run-tests 'namejen.markov-test)
