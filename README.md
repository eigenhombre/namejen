# namejen

Random name generator, written in Clojure, patterned after 
http://roguebasin.roguelikedevelopment.org/index.php/Markov_chains_name_generator_in_Python

This code uses Markov chains and a list of source names, along with a
few rules to increase the fun a little bit.  The output is a column of
names suitable for games, writing fiction, naming your pets and kids,
etc.  Your mileage may vary.

Substituting a different list of source names will change the flavor
of the names, sometimes dramatically.  

## Examples

    Miss Rite Line
    Mme. Ilson Huvra Corris Vince Kevyn, Esq.
    Elix
    Rlene Tanly
    Mrs. Thias Otta III, LCPT
    Gypsy Tewart Jaak Gunter Stie Sr.
    Ohammad Heodore
    Anly Inricharleen
    Erbert Strakash
    Sr. Marguerite Arissa IV
    Ruth Gabriel Eresa Tanaka Gang
    Think Rgot Sir
    Ohammad Heodore
    Herr Dmond Ralph
    Mrs. Kael Olfe
    Italynnette Ekar, Esq.
    Awan Mneek Ohong, Ph.D.
    Orma Ryan Artin Natolerant Imitro
    Anya Think
    Ucifer Konstantinos Cobson
    Raul Well
    Sehyo Irofumi Vilhelm III
    Miss Page Steen
    Sir Ilya Inod Holas Sumu Ncois
    Main Case, MD

## Usage

    lein uberjar
    java -jar namejen-1.0.0-SNAPSHOT-standalone.jar

## (Continuous) Testing

    lein autoexpect

## License

Copyright (C) 2012 John Jacobsen.

Distributed under the Eclipse Public License, the same as Clojure.
