(defproject eigenhombre/namejen "0.1.15-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :url "https://github.com/eigenhombre/namejen"
  :resource-paths ["resources"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aliases {"autotest" ["midje" ":autotest"]}
  :description "A Markov-chain-based name generator for games, fiction, &c."
  :deploy-repositories [["releases" :clojars]]
  :main ^:skip-aot namejen.core
  :uberjar-name "namejen.jar"
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}
             :uberjar {:aot :all}}
  :scm {:name "git"
        :url "https://github.com/eigenhombre/namejen"})
