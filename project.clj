(defproject eigenhombre/namejen "0.1.2"
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :url "https://github.com/eigenhombre/namejen"
  :resource-paths ["resources"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main namejen.core
  :aliases {"autotest" ["midje" ":autotest"]}
  :description "A Markov-chain-based name generator for games, fiction, &c.")
