(ns namejen.names
  (:require [clojure.pprint :refer [cl-format]]
            [clojure.string :as string]
            [namejen.lists.names :as nl]
            [namejen.lists.female-names :as fnl]
            [namejen.lists.male-names :as mnl]
            [namejen.lists.latin-words :as lwl]
            [namejen.markov :refer [generate-single-name
                                    build-map-from-strings]]))

(defn ^:private gender []
  (->> [:male :female]
       (repeat 20)
       (apply concat)
       (concat [:other])
       rand-nth))

(def ^:private male-prefixes (concat (repeat 10 "Mr.")
                                     ["M." "Sr." "Herr" "Sir"]))

(def ^:private female-prefixes (concat (repeat 10 "Ms.")
                                       ["Miss" "Mrs." "Sra." "Srta." "Fr."]))

(def ^:private gender-neutral-prefixes `("Mx." ~@(repeat 10 nil)))

;; FIXME: Male-heavy.  Female equivalents?
(defn ^:private generation []
  (rand-nth ["Sr." "Jr." "Jr." "I" "II" "III" "III" "IV" "V"]))

(defn ^:private choose-whether [n]
  (zero? (rand-int n)))

(defn ^:private num-names [] (rand-nth (concat (repeat 5 2)
                                               [1 3 3 4 5])))

(defn ^:private name-map-from-multi-lists [namelists]
  (->> namelists
       (mapcat (partial map str))
       (build-map-from-strings 4)))

(defn ^:private name-map-from-list [namelist]
  (->> namelist
       (map str)
       (build-map-from-strings 4)))

(def ^:private all-nextmap
  (name-map-from-multi-lists [nl/names-list
                              mnl/male-names-list
                              fnl/female-names-list
                              lwl/latin-words-list]))

(def ^:private default-nextmap (name-map-from-list nl/names-list))
(def ^:private male-name-map (name-map-from-list mnl/male-names-list))
(def ^:private female-name-map (name-map-from-list fnl/female-names-list))
(def ^:private latin-name-map (name-map-from-list lwl/latin-words-list))

(defn male-name
  "
  Examples:

      (repeatedly 5 male-name)
      ;;=>
      (\"Daren\" \"Istian\" \"Amion\" \"Lonnie\" \"Ichary\")

  "
  {:doc/format :markdown}
  []
  (generate-single-name male-name-map))

(defn female-name
  "
  Examples:

      (repeatedly 5 female-name)
      ;;=>
      (\"Kaela\" \"Erdie\" \"Ethyl\" \"Elnora\" \"Genny\")

  "
  {:doc/format :markdown}
  []
  (generate-single-name female-name-map))

(defn generic-name
  "
  Examples:

      (repeatedly 5 generic-name)
      ;;=>
      (\"Lutabrus\" \"Pono\" \"Ognosco\" \"Elta\" \"Ngus\")

  "
  {:doc/format :markdown}
  []
  (generate-single-name all-nextmap))

(defn latinish-name
  "
  Examples:

      (repeatedly 5 latinish-name)
      ;;=>
      (\"Psio\" \"Ongruus\" \"Agri\" \"Itacitero\" \"Aedo\")

  "
  {:doc/format :markdown}
  []
  (generate-single-name latin-name-map))

(defn gen-name-data-as-map
  "
  Example:

      (gen-name-data-as-map)
      ;;=>
      {:gender :female
       :first-name \"Luette\"
       :prefix nil
       :surnames [\"Adlai\" \"Angela\" \"Stlik\"]
       :generation nil}

  "
  {:doc/format :markdown}
  []
  (let [gender (gender)
        first-name (condp = gender
                     :male (male-name)
                     :female (female-name)
                     :other (generic-name))
        surnames (->> default-nextmap
                      (partial generate-single-name)
                      (repeatedly (dec (num-names)))
                      vec)]
    {:gender gender
     :first-name first-name
     :prefix (when (choose-whether 3)
               (rand-nth (condp = gender
                           :male male-prefixes
                           :female female-prefixes
                           :other gender-neutral-prefixes)))
     :surnames surnames
     :generation (when (and (choose-whether 4)
                            (= gender :male))
                   (generation))}))

(defn format-name-map [{:keys [gender prefix first-name surnames generation]}]
  (string/join `(~@(if (and prefix
                            (seq surnames)) [prefix " "])
                 ~first-name
                 ~(if (seq surnames) " " "")
                 ~@(interpose " " surnames)
                 ~@(if (and (seq surnames)
                            generation) [", " generation]))))

(defn name-maker
  "
  Example:

      (repeatedly 10 name-maker)
      ;;=>
      '(\"Deidra Olas Rafael\"
        \"Ms. Rochellye Ryce\"
        \"Xuan Amiltos\"
        \"Mesha\"
        \"Mr. Rain Brian\"
        \"Ms. Rnelieselottie Tendra\"
        \"Srta. Nora Adrianto\"
        \"Larence Holly, Jr.\"
        \"Ms. Cheryn Ning\"
        \"Mr. Nces Line Herbertran\")

  "
  {:doc/format :markdown}
  []
  (format-name-map (gen-name-data-as-map)))
