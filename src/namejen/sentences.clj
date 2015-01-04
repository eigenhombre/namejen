(ns namejen.sentences
  (:require [namejen.markov :refer [build-map-from-seqs
                                    generate-sequence]]))


;; Cache text from Project Gutenberg.
(def ^:private siddhartha (atom nil))
(defn- get-siddhartha []
  (if-let [s @siddhartha]
    s
    (reset! siddhartha
            (slurp "https://www.gutenberg.org/cache/epub/2500/pg2500.txt"))))


(defn string-tokens
  "
  Split string on whitespace and remove extraneous empty strings.
  "
  [s] (->> s
           (#(clojure.string/split % #" "))
           (remove #{""})))


(defn sentences
  "
  Fetch text, break into a collection of sentences (sequences of tokens).
  "
  []
  (->> (get-siddhartha)
       (drop 860) ;; FIXME: Make this smarter
       (remove #{\return \"})
       (map (fn [c] (if (= c \newline) \space c)))
       (apply str)
       (#(clojure.string/split % #"\."))
       (map clojure.string/lower-case)
       (map string-tokens)))


;;(take 3 (sentences))
;;=>
'(("in" "the" "shade" "of" "the" "house," "in" "the" "sunshine" "of"
"the" "riverbank" "near" "the" "boats," "in" "the" "shade" "of" "the"
"sal-wood" "forest," "in" "the" "shade" "of" "the" "fig" "tree" "is"
"where" "siddhartha" "grew" "up," "the" "handsome" "son" "of" "the"
"brahman," "the" "young" "falcon," "together" "with" "his" "friend"
"govinda," "son" "of" "a" "brahman") ("the" "sun" "tanned" "his"
"light" "shoulders" "by" "the" "banks" "of" "the" "river" "when"
"bathing," "performing" "the" "sacred" "ablutions," "the" "sacred"
"offerings") ("in" "the" "mango" "grove," "shade" "poured" "into"
"his" "black" "eyes," "when" "playing" "as" "a" "boy," "when" "his"
"mother" "sang," "when" "the" "sacred" "offerings" "were" "made,"
"when" "his" "father," "the" "scholar," "taught" "him," "when" "the"
"wise" "men" "talked"))


(defn sentence-map
  "
  Build Markov transition map of possible sentences.
  "
  []
  (->> (sentences)
       (build-map-from-seqs 3)))


(defn generate-sentence
  "
  Generate sample sentence from the given map.
  "
  [smap]
  (->> smap
       (generate-sequence)
       ((fn [[h & t]]
          (cons (clojure.string/capitalize h)
                t)))
       ((partial clojure.string/join " "))
       (#(str % "."))))


;;(repeatedly 10 #(generate-sentence (sentence-map)))
;;=>
'("Books, only because he had believed in me and now believes in him,
   who had been shivering with grief since those ranting speeches, the
   boy had attended his mother's funeral; gloomy and shy, he had
   listened to siddhartha, who greeted him as his friend, his
   companion, his servant, his spear-carrier, his shadow."
  "In time, gets stains, gets wrinkles, gets worn off at the seams,
   and starts to show threadbare spots here and there, disappointment
   and disgust were waiting."
  "Is this very fact, that it is posted with permission of the
   copyright holder found at the foundation's web site and official page
   at http://pglaf."
  "Had asked the hornbill-bird or the chimpanzee."
  "For him, you would not be angry with me, said the young man."
  "Her son, she had gone on her way due to the news of the near death
   of gotama, in simple clothes, on foot."
  "Lanes of the town with the luminous forehead, with the eye of a
   king, and of a woman giving birth, and of a warrior, and of a bird of
   the night, and of a warrior, and of a bird of the night, siddhartha
   left his garden, left the city, and he is also worrying me."
  "On when i had fallen asleep in the forest, but had then turned back
   to his work."
  "Trying to tell you? don't you see that he doesn't want to be
   followed? but he did not tie his soul to any particular voice and
   submerged his self into thousands of other forms, was an animal, was
   carrion, was stone, was wood, was water, and awoke every time to find
   his old self again, sun shone or moon, was his self again, turned
   round in the cycle, felt thirst, overcame the thirst, felt new
   thirst."
  "Of this game was sansara, a murky source, dark waters.")
