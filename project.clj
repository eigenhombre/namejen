(defproject namejen/namejen "0.1.0-SNAPSHOT" 
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :profiles {:dev {:dependencies [[expectations "1.3.6"]]}}
  :url "https://github.com/eigenhombre/namejen"
  :resource-paths ["resources"]
  :main namejen.core
  :min-lein-version "2.0.0"
  :plugins [[lein-expectations "0.0.1"] [lein-autoexpect "0.1.1"]]
  :description "A Markov-chain-based name generator for games, fiction, et cetera")
