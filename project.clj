(defproject eigenhombre/namejen "0.1.23"
  :url "https://github.com/eigenhombre/namejen"
  :resource-paths ["resources"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :description "A Markov-chain-based name generator for games, fiction, &c."
  :deploy-repositories [["releases" :clojars]]
  :main ^:skip-aot namejen.main
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]
            "update-version-in-readme" ["file-replace" "README.md"
                                        "\\[eigenhombre/namejen \"" "\"]"
                                        "version"]}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.891"]]
  :plugins [[lein-cljsbuild "1.1.8"]
            [lein-doo "0.1.11"]]
  :profiles {:dev {:dependencies []
                   :plugins [[jonase/eastwood "0.9.9"]
                             [lein-bikeshed "0.5.2"]
                             [lein-codox "0.10.8"]
                             [lein-file-replace "0.1.0"]
                             [lein-kibit "0.1.8"]]}
             :kaocha {:dependencies [[lambdaisland/kaocha "1.0.887"]]}
             :uberjar {:aot :all}}
  :codox {:output-path "docs"
          :source-uri
          {#".*"
           "https://github.com/eigenhombre/namejen/blob/master/{filepath}#L{line}"}}
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure"]
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
                  ["vcs" "push"]]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/main/clojure" "src/test/clojure"]
                        :jar true
                        :compiler {:optimizations :whitespace
                                   :output-dir "target/cljs/dev"
                                   :output-to "target/cljs/namejen_dev.js"
                                   :pretty-print true}}
                       {:id "test"
                        :source-paths ["src/main/clojure" "src/test/clojure"]
                        :compiler {:main test.clojure.namejen.test-runner
                                   :output-to "target/testable.js"
                                   :target :nodejs
                                   :optimizations :none}}
                       {:id "prod"
                        :source-paths ["src/main/clojure" "src/test/clojure"]
                        :compiler {:optimizations :advanced
                                   :output-dir "target/cljs/prod"
                                   :output-to "target/cljs/namejen_prod.js"
                                   :pretty-print false}}]})
