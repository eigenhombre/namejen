(ns namejen.sentences)


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
  (->> (get-siddhartha)
       (drop 860) ;; FIXME: Make this smarter
       (remove #{\return \"})
       (map (fn [c] (if (= c \newline) \space c)))
       (apply str)
       (#(clojure.string/split % #"\."))
       (map string-tokens)))


;;(take 3 sentences)
;;=>
'(("In" "the" "shade" "of" "the" "house," "in" "the" "sunshine" "of"
"the" "riverbank" "near" "the" "boats," "in" "the" "shade" "of" "the"
"Sal-wood" "forest," "in" "the" "shade" "of" "the" "fig" "tree" "is"
"where" "Siddhartha" "grew" "up," "the" "handsome" "son" "of" "the"
"Brahman," "the" "young" "falcon," "together" "with" "his" "friend"
"Govinda," "son" "of" "a" "Brahman") ("The" "sun" "tanned" "his"
"light" "shoulders" "by" "the" "banks" "of" "the" "river" "when"
"bathing," "performing" "the" "sacred" "ablutions," "the" "sacred"
"offerings") ("In" "the" "mango" "grove," "shade" "poured" "into"
"his" "black" "eyes," "when" "playing" "as" "a" "boy," "when" "his"
"mother" "sang," "when" "the" "sacred" "offerings" "were" "made,"
"when" "his" "father," "the" "scholar," "taught" "him," "when" "the"
"wise" "men" "talked"))


