# namejen

<img src="/quoque.jpg" width="400">

![build](https://github.com/eigenhombre/namejen/actions/workflows/build.yml/badge.svg)

Random name generator, written in Clojure, that uses Markov chains to
follow common syllabic patterns.  Inspired by [this Python project](http://www.roguebasin.com/index.php?title=Markov_chains_name_generator_in_Python), and somewhat dual to [this Common Lisp library](https://github.com/eigenhombre/nominal).

When run standalone, the output is a column of names suitable for
games, writing fiction, naming your pets and kids, etc.  Your mileage
may vary.

Substituting a different list of source names (see files in `resources`)
will change the flavor of the names, sometimes dramatically.  For the
input provided, the output seems reminiscent of names from an
[Iain M. Banks novel](http://en.wikipedia.org/wiki/Iain_Banks).

## Examples

    Deidra Olas Rafael
    Ms. Rochellye Ryce
    Xuan Amiltos
    Mr. Rain Brian
    Ms. Rnelieselottie Tendra
    Srta. Nora Adrianto
    Larence Holly, Jr.
    Ms. Cheryn Ning
    Ms. Ursula Anity Todd
    Gilda Rleen Cobson Gabrina
    Herr Chary Acey Trian, V
    Sra. Idre Izumi Jeri
    Mr. Ufus Zabeth Leslie Klaus
    Octavio Llen Stian Steen Ergeant
    Ms. Jerilynn Pratap
    Ms. Ronna Lastic
    Hiroko Spencer
    Mr. Jaime Odent
    Basil
    Aude Dirk, Sr.
    Erisa Doss
    Lake Main, Jr.
    Aniqua Hsin
    Ms. Mackenzie Tofer Oshua
    Jasonya Etry
    Mora Aryl Raif
    Sr. Obias Kerry, V
    Lane Dwight Rouk
    Mr. Noah Alter Giovanni
    Ms. Rucila Clyde
    Srta. Ulinetta Alastair
    Grice Erdar
    Arrell Oyuki, Jr.
    Milton Farouk, II
    Herr Dylan Ewis

## Usage

### As a standalone program

    make                                  # once
    java -jar target/uberjar/namejen.jar  # every time

### As a library

Add to your `project.clj` or `deps.edn`:

```
    [eigenhombre/namejen "0.1.20"]
```

The functions of interest are in the `namejen.names` namespace:

- [gen-name-data-as-map](https://github.com/eigenhombre/namejen/blob/master/src/namejen/names.clj#L49) generates gender, first name,
  honorific/prefix, surname(s), and generation info (Jr., III, etc.).
- [format-name-map](https://github.com/eigenhombre/namejen/blob/master/src/namejen/names.clj#L70) formats the output of the previous function into
  something readable.
- The [name-maker](https://github.com/eigenhombre/namejen/blob/master/src/namejen/names.clj#L70) function is the composition of the two functions,
  for convenience.

### TO DO list:
- Allow users to specify source of names
- Allow users to specify Markov chain lengths. The longer the Markov
  chain length, the more the names will sound like "real" names but
  the less variations you will get.

## License

Copyright (C) 2012-2021 John Jacobsen.

Distributed under the Eclipse Public License, the same as Clojure.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT
OF THIRD PARTY RIGHTS. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
