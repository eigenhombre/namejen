(defproject namejen/namejen "0.1.0"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :url "https://github.com/eigenhombre/namejen"
  :resource-paths ["resources"]
  :main namejen.core
  :aliases {"autotest" ["midje" ":autotest"]}
  :description "A Markov-chain-based name generator for games, fiction, &c.")
