(ns namejen.companies
  (:require [clojure.string :as string]
            [namejen.markov :refer [build-map-from-strings
                                    generate-single-name]]
            [namejen.lists.companies :as cl]))

(def ^:private company-data-map
  (build-map-from-strings 4 cl/companies-list))

(defn ^:private num-company-names []
  (rand-nth [1 1 1 2 2 3 4]))

(defn ^:private corptype []
  (rand-nth ["LLC"
             "Incorporated"
             "Inc."
             "Incorporated"
             "Inc."
             "SA"
             "AG"]))

(defn ^:private domain []
  (rand-nth ["com" "com" "com" "net" "us" "info" "tv" "biz"]))

(defn ^:private normalize [s]
  (-> s
      (string/replace #"\'|\&" "")
      (string/replace #"^-" "")))

(defn gen-company
  "
  Generate a fake company and its website.  Very very alpha.
  "
  []
  (let [num-names (num-company-names)
        companies (repeatedly num-names (partial generate-single-name
                                                 company-data-map))
        compname
        (str (clojure.string/join " " companies)
             (if (zero? (rand-nth (range 4)))
               (str "")
               (str ", "
                    (corptype))))
        website (str (->> companies
                          (take 3)
                          (map normalize)
                          (map clojure.string/lower-case)
                          (clojure.string/join (rand-nth ["-" "" "."])))
                     "."
                     (domain))]
    {:name compname
     :website website}))

(comment
  (->> gen-company
       (repeatedly 20)
       (map (juxt :name :website)))
  ;;=>
  (["Vance, SA" "vance.net"]
   ["Tozone" "tozone.tv"]
   ["A-cola" "a-cola.biz"]
   ["Rrah's Ckard, Incorporated" "rrahsckard.biz"]
   ["Mpbellstar-tv L-mart, Inc." "mpbellstar-tvl-mart.us"]
   ["Sterna Bergen Flowers" "sterna.bergen.flowers.com"]
   ["Edison, Incorporated" "edison.com"]
   ["Adams T-pacific" "adams.t-pacific.info"]
   ["Oneer-standarden Precision Live Nagra, SA"
    "oneer-standarden.precision.live.us"]
   ["Inens, LLC" "inens.tv"]
   ["Ynegy, Inc." "ynegy.com"]
   ["Innacle" "innacle.net"]
   ["Rathon Trade Northermance Pervalue"
    "rathon.trade.northermance.biz"]
   ["Kerr-mcgee Rlisle Eader's, Incorporated"
    "kerr-mcgeerlisleeaders.us"]
   ["Roger" "roger.info"]
   ["Ap-on" "ap-on.us"]
   ["Costco" "costco.tv"]
   ["Contington Pittston Minion" "contington.pittston.minion.info"]
   ["Systems, Incorporated" "systems.tv"]
   ["Hughes Lucenturytel" "hughes.lucenturytel.com"]))
