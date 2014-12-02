(ns namejen.companies
  (:require [namejen.markov :refer [generate-single-name
                                    get-name-data
                                    map-for-name-data]]))


(def ^:private company-data-map (->> "companies.txt"
                                     get-name-data
                                     (map-for-name-data 4)))


(defn num-company-names [] (rand-nth [1 1 1 2 2 3 4]))


(defn corptype [] (rand-nth ["LLC"
                             "Incorporated"
                             "Inc."
                             "Incorporated"
                             "Inc."
                             "SA"
                             "AG"]))


(defn domain [] (rand-nth ["com" "com" "com" "net" "us" "info" "tv" "biz"]))


(defn remove-hyphens [s] (apply str (remove #{\' \&} s)))


(defn gen-company-name-and-website []
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
                          (map remove-hyphens)
                          (map clojure.string/lower-case)
                          (clojure.string/join (rand-nth ["-" "" "."])))
                     "."
                     (domain))]
    [compname website]))


(comment
  (repeatedly 20 gen-company-name-and-website)

  ;;=>
  (["Tolive" "tolive.com"]
   ["Phone Mnicor" "phone.mnicor.com"]
   ["Mercial -cola Banknortek" "mercial.-cola.banknortek.com"]
   ["Eluxe, Incorporated" "eluxe.com"]
   ["Tific" "tific.com"]
   ["Anley Gemstar, Incorporated" "anleygemstar.net"]
   ["Laboratory Ilicon, AG" "laboratoryilicon.info"]
   ["Teco, SA" "teco.com"]
   ["Pfizer Valspartment Acrporatory Chaels, AG"
    "pfizer-valspartment-acrporatory.info"]
   ["Ifax L-myers, Incorporated" "ifaxl-myers.com"]
   ["Leodusa Paychex, Incorporated" "leodusa-paychex.com"]
   ["Erck, Incorporated" "erck.us"]
   ["Rgia Hercules Engelhard Rtford, Inc." "rgia.hercules.engelhard.net"]
   ["Dentair, Incorporated" "dentair.biz"]
   ["E*trade" "e*trade.us"]
   ["Rated Everly-clark" "ratedeverly-clark.tv"]
   ["Onati Lcase Uintiles, SA" "onatilcaseuintiles.us"]
   ["Olidated Maytag Nova, Inc." "olidatedmaytagnova.com"]
   ["Adio Evrontex Almolive, AG" "adioevrontexalmolive.com"]
   ["Ana-packaging, Incorporated" "ana-packaging.com"])


  )
