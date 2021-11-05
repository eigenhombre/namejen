(defproject eigenhombre/namejen "0.1.21-SNAPSHOT"
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
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]
            "update-version-in-readme" ["file-replace" "README.md"
                                        "\\[eigenhombre/namejen \"" "\"]"
                                        "version"]}
  :profiles {:dev {:dependencies []
                   :plugins [[lein-bikeshed "0.5.2"]
                             [jonase/eastwood "0.9.9"]
                             [lein-file-replace "0.1.0"]
                             [lein-kibit "0.1.8"]]}
             :kaocha {:dependencies [[lambdaisland/kaocha "1.0.887"]]}
             :uberjar {:aot :all}}
  :scm {:name "git"
        :url "https://github.com/eigenhombre/namejen"}
  :repositories [["releases" {:url "https://repo.clojars.org"
                              :creds :gpg}]]
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version"
                   "leiningen.release/bump-version"
                   "release"]
                  ["file-replace" "README.md"
                   "\\[eigenhombre/namejen \"" "\"]"
                   "version"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy" "clojars"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]])
