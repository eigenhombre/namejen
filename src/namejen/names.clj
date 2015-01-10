(ns namejen.names
  (:require [namejen.markov :refer [generate-single-name
                                    build-map-from-strings]]
            [namejen.io :refer [get-name-data]]))


(defn gender [] (->> [:male :female]
                     (repeat 20)
                     (apply concat)
                     (concat [:other])
                     rand-nth))


(def male-prefixes (concat (repeat 10 "Mr.")
                           ["M." "Sr." "Herr" "Sir"]))


(def female-prefixes (concat (repeat 10 "Ms.")
                             ["Miss" "Mrs." "Sra." "Srta." "Fr."]))


;; FIXME: Male-heavy.  Female equivalents?
(defn generation [] (rand-nth ["Sr." "Jr." "Jr."
                               "I" "II" "III" "III" "IV" "V"]))


(defn choose-whether [n] (= (rand-int n) 0))


(defn num-names [] (rand-nth (concat (repeat 5 2)
                                     [1 3 3 4 5])))


(defn name-map-from-resource-file [fname]
  (->> fname
       get-name-data
       (build-map-from-strings 4)))


(def default-nextmap (name-map-from-resource-file "names.txt"))
(def male-name-map (name-map-from-resource-file "male-names.txt"))
(def female-name-map (name-map-from-resource-file "female-names.txt"))


(defn male-name [] (generate-single-name male-name-map))
(defn female-name [] (generate-single-name female-name-map))


(defn gen-name-data-as-map []
  (let [gender (gender)
        first-name (condp = gender
                     :male (male-name)
                     :female (female-name)
                     :other ((rand-nth [male-name female-name])))
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
                           :other (concat male-prefixes female-prefixes))))
     :surnames surnames
     :generation (when (and (choose-whether 4)
                            (= gender :male))
                   (generation))}))


(defn format-name-map [{:keys [gender prefix first-name surnames generation]}]
  (apply str `(~@(if (and prefix
                          (seq surnames)) [prefix " "])
               ~first-name
               ~(if (seq surnames) " " "")
               ~@(interpose " " surnames)
               ~@(if (and (seq surnames)
                          generation) [", " generation]))))


(defn name-maker [] (format-name-map (gen-name-data-as-map)))


(repeatedly 100 name-maker)

;;=>
'("Deidra Olas Rafael"
  "Ms. Rochellye Ryce"
  "Xuan Amiltos"
  "Mesha"
  "Mr. Rain Brian"
  "Ms. Rnelieselottie Tendra"
  "Srta. Nora Adrianto"
  "Larence Holly, Jr."
  "Ms. Cheryn Ning"
  "Mr. Nces Line Herbertran"
  "Ngelyndi Rleen Hnath"
  "Dford Uane Raja"
  "Ms. Ursula Anity Todd"
  "Gilda Rleen Cobson Gabrina"
  "Herr Chary Acey Trian, V"
  "Sra. Idre Izumi Jeri"
  "Seth Rtmanny"
  "Mr. Ufus Zabeth Leslie Klaus"
  "Uddy"
  "Mr. Rtez Mats"
  "Osphine Xannette"
  "Mr. Teddy Rray"
  "Octavio Llen Stian Steen Ergeant"
  "Ms. Jerilynn Pratap"
  "Ms. Ronna Lastic"
  "Hiroko Spencer"
  "Obias Susanne Pper Xandell Helm"
  "Mr. Jaime Odent"
  "Ms. Thew Terrencer Rchie Barton"
  "Ms. Meraldine Agnar"
  "Basil"
  "Ictor Jill Uashi Lainer"
  "Aude Dirk, Sr."
  "Dmundo Amiya"
  "Alorita Bernie"
  "Sphina Akash"
  "Ssaunda Pert"
  "Erisa Doss"
  "Aude Avery"
  "Liff Howard"
  "Rhett Rbra"
  "Sir Uart Emmett"
  "Bernie Anny"
  "Mr. Ospeh Rtis Stefan Rofumi Tonella"
  "Ifford"
  "Ms. Hton Timo Fael Rtney"
  "Bara Rthur"
  "M. Tney Jonath"
  "Lien Toshi Reiner Erite Eenu"
  "Mr. Stewart Suan"
  "Mr. Sreal Anao Torsten, Jr."
  "Edmund Rick"
  "Mr. Irley Anjeev"
  "Yden Eymour Nifer Njit Nnie, Sr."
  "Tlyn Nold Orah"
  "Ladonna Curtis"
  "Racelyn Tovah Lyndon"
  "Mrs. Rlys Ndell Xana Russell"
  "Ms. Renna Ufic"
  "Orrison Soohong, I"
  "Liott Urel, Jr."
  "Sra. Lessika Armi"
  "Sir Anford Huey Skef Hartmann Usty"
  "Ms. Evetta Iane"
  "Arell Rlos Caleb Exanderson"
  "Ms. Rren Arney"
  "Rgot Hienz"
  "Levia"
  "Coreena Icholas"
  "Herr Omeo Riggs, Sr."
  "Miss Oras Eggy Toby"
  "Aila"
  "Amisha"
  "Wonda Danni"
  "Miss Rilou Idhyanatoly Chuck"
  "Arta Raja"
  "Herr Lair Olkka"
  "Mr. Rady Rtha"
  "Kary Foklis Ofia Aleb"
  "Mr. Iego Cisco"
  "Lincoln Ario Orrainer, Jr."
  "Lake Main, Jr."
  "Numbery"
  "Aniqua Hsin"
  "Orileen Errancois"
  "Rray Ouiqa"
  "Ms. Mackenzie Tofer Oshua"
  "Jasonya Etry"
  "Mora Aryl Raif"
  "Sr. Obias Kerry, V"
  "Lane Dwight Rouk"
  "Mr. Noah Alter Giovanni"
  "Ms. Rucila Clyde"
  "Srta. Ulinetta Alastair"
  "Grice Erdar"
  "Arrell Oyuki, Jr."
  "Milton Farouk, II"
  "Vory Cris"
  "Lynisha"
  "Herr Dylan Ewis")

