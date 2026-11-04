(ns build
  (:require [cljs.build.api :as cljs]
            [clojure.string :as string]
            [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'eigenhombre/namejen)
(def repo-url "https://github.com/eigenhombre/namejen")
(def version-file "VERSION")
(def src-dirs ["src/main/clojure" "resources"])
(def class-dir "target/classes")
(def basis (delay (b/create-basis {:project "deps.edn"})))

(def cljs-builds
  [{:optimizations :whitespace
    :output-dir "target/cljs/dev"
    :output-to "target/cljs/namejen_dev.js"
    :pretty-print true}
   {:optimizations :advanced
    :output-dir "target/cljs/prod"
    :output-to "target/cljs/namejen_prod.js"
    :pretty-print false}])

(defn- version [] (string/trim (slurp version-file)))

(defn- jar-file [] (format "target/%s-%s.jar" (name lib) (version)))

(defn- uber-file []
  (format "target/%s-%s-standalone.jar" (name lib) (version)))

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (b/delete {:path class-dir})
  (b/write-pom
   {:class-dir class-dir
    :lib lib
    :version (version)
    :basis @basis
    :src-dirs ["src/main/clojure"]
    :scm {:url repo-url
          :connection "scm:git:git://github.com/eigenhombre/namejen.git"
          :tag (str "v" (version))}
    :pom-data
    [[:description
      "A Markov-chain-based name generator for games, fiction, &c."]
     [:url repo-url]
     [:licenses
      [:license
       [:name "Eclipse Public License"]
       [:url "http://www.eclipse.org/legal/epl-v10.html"]]]]})
  (b/copy-dir {:src-dirs src-dirs :target-dir class-dir})
  (b/jar {:class-dir class-dir :jar-file (jar-file)}))

(defn uber [_]
  (b/delete {:path class-dir})
  (b/copy-dir {:src-dirs src-dirs :target-dir class-dir})
  (b/compile-clj {:basis @basis
                  :src-dirs ["src/main/clojure"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file (uber-file)
           :basis @basis
           :main 'namejen.main}))

(defn cljs [_]
  (doseq [opts cljs-builds]
    (cljs/build "src/main/clojure" opts)))

(defn- git [& args]
  (let [{:keys [exit out err]} (b/process {:command-args (into ["git"] args)
                                           :out :capture
                                           :err :capture})]
    (when-not (zero? exit)
      (throw (ex-info (str "git " (string/join " " args) " failed: " err)
                      {:exit exit})))
    out))

(defn- set-version! [v]
  (spit version-file (str v "\n")))

(defn- update-readme-version! [v]
  (let [readme (slurp "README.md")]
    (spit "README.md"
          (string/replace
           readme
           #"(eigenhombre/namejen (?:\{:mvn/version )?)\"[^\"]+\""
           (str "$1\"" v "\"")))))

(defn- next-snapshot [v]
  (let [[major minor patch] (string/split v #"\.")]
    (format "%s.%s.%d-SNAPSHOT" major minor (inc (parse-long patch)))))

(defn- commit! [msg & paths]
  (apply git "add" paths)
  (git "commit" "-m" msg))

(defn- assert-deploy-env! []
  (let [missing (remove System/getenv ["CLOJARS_USERNAME" "CLOJARS_PASSWORD"])]
    (when (seq missing)
      (throw (ex-info (str "Missing env vars (see doc/releasing.md): "
                           (string/join ", " missing))
                      {})))))

(defn deploy [_]
  (assert-deploy-env!)
  (jar nil)
  (dd/deploy {:installer :remote
              :artifact (b/resolve-path (jar-file))
              :pom-file (b/pom-path {:lib lib :class-dir class-dir})
              :sign-releases? true}))

(defn release
  "
  Equivalent of `lein release`: drop -SNAPSHOT, update README, commit,
  tag, deploy to Clojars, bump to next -SNAPSHOT, commit, and push.
  "
  [_]
  (assert-deploy-env!)
  (when-not (string/blank? (git "status" "--porcelain"))
    (throw (ex-info "Working tree is not clean." {})))
  (let [v (string/replace (version) #"-SNAPSHOT$" "")]
    (set-version! v)
    (update-readme-version! v)
    (commit! (str "Version " v) version-file "README.md")
    (git "tag" "-a" (str "v" v) "-m" (str "Release " v))
    (deploy nil)
    (let [snapshot (next-snapshot v)]
      (set-version! snapshot)
      (commit! (str "Version " snapshot) version-file))
    (git "push" "--follow-tags")))
