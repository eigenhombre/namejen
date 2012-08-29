# namejen

Random name generator, written in Clojure, patterned after [1].

This code uses Markov chains and a list of source names, along with a
few rules to increase the fun a little bit.  The output is a column of
names suitable for games, writing fiction, naming your pets and kids,
etc.  Your mileage may vary.

Substituting a different list of source names (resources/names.txt)
will change the flavor of the names, sometimes dramatically.  For the
input provided, the output seems reminiscent of names from an Iain
M. Banks novel [2].

[1] http://roguebasin.roguelikedevelopment.org/index.php/Markov_chains_name_generator_in_Python

[2] http://en.wikipedia.org/wiki/Iain_Banks

## Examples

    Miss Rite Line
    Mme. Ilson Huvra Corris Vince Kevyn, Esq.
    Elix
    Rlene Tanly
    Mrs. Thias Otta III, LCPT
    Gypsy Tewart Jaak Gunter Stie Sr.
    Ohammad Heodore
    Erbert Strakash
    Sr. Marguerite Arissa IV
    Ruth Gabriel Eresa Tanaka Gang
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
    Barryl Tahsin Hyam Ctor Icky
    Aola Chip, LCPT
    Sofoklis Deirdre
    Rwin Butler
    Argie Less
    Asanobu Oshua
    Herr Malloy Jr.
    Krzysztof Frederick Jr.

## Usage

To use as a standalone program:

    lein uberjar
    java -jar namejen-1.0.0-SNAPSHOT-standalone.jar

To use as a library function, add the following to your project.clj:

    [namejen "0.1.0-SNAPSHOT"]

Then, in your code's namespace declaration:

    (:use [namejen.core :only [get-default-name-data name-maker]])

Finally,

    (def make-name (name-maker 4 (get-default-name-data)))

will create a function which will return a new name each time it is
called.

The function get-default-name-data returns a long list of names stored
in the library's jar file, separated by newlines; 4 is the Markov
chain length.  You can play with different name inputs by simply
supplying a long string of names (again, separated by newlines)
instead of the call to get-default-name-data.  The longer the Markov
chain length, the more the names will sound like "real" names but the
less variations you will get.


## (Continuous) Testing

Yes, there are a few tests.  I use the excellent expectations module[3] with the Leiningen autoexpect plugin:

    lein autoexpect  # See project.clj and src/namejen/core.clj for setup

[3] https://github.com/jaycfields/expectations

## License

Copyright (C) 2012 John Jacobsen.

Distributed under the Eclipse Public License, the same as Clojure.
