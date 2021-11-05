(defproject eigenhombre/namejen "0.1.15-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :url "https://github.com/eigenhombre/namejen"
  :resource-paths ["resources"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :description "A Markov-chain-based name generator for games, fiction, &c."
  :deploy-repositories [["releases" :clojars]]
  :main ^:skip-aot namejen.core
  :uberjar-name "namejen.jar"
  :target-path "target/%s"
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]}
  :profiles {:dev {:dependencies []}
             :kaocha {:dependencies [[lambdaisland/kaocha "1.0.887"]]}
             :uberjar {:aot :all}}
  :scm {:name "git"
        :url "https://github.com/eigenhombre/namejen"})
