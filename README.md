# namejen

Random name generator, written in Clojure, patterned after 
http://roguebasin.roguelikedevelopment.org/index.php/Markov_chains_name_generator_in_Python

This code uses Markov chains and a list of source names, along with a
few rules to increase the fun a little bit.  The output is a column of
names suitable for games, writing fiction, naming your pets and kids,
etc.  Your mileage may vary.

Substituting a different list of source names will change the flavor
of the names, sometimes dramatically.

## Usage

    lein uberjar
    java -jar namejen-1.0.0-SNAPSHOT-standalone.jar

## (Continuous) Testing

    lein autoexpect

## License

Copyright (C) 2012 John Jacobsen.

Distributed under the Eclipse Public License, the same as Clojure.
