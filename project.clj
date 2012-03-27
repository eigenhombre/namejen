(defproject namejen "1.0.0-SNAPSHOT"
  :description "A Markov-chain-based name generator for games, fiction, etc."
  :main namejen.core
  :resource-paths ["resources"] ; non-code files included in classpath/jar
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :dev-dependencies [[lein-expectations "0.0.1"]
                     [lein-autoexpect "0.1.1"]
                     [expectations "1.3.6"]])
