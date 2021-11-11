# namejen

<img src="/quoque.jpg" width="400">

![build](https://github.com/eigenhombre/namejen/actions/workflows/build.yml/badge.svg)

Random name generator, written in Clojure / ClojureScript, that uses Markov chains to
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

    make                                             # once
    java -jar target/$(ls target | grep standalone)  # each time

### As a library

Add to your `project.clj` or `deps.edn`:

    [eigenhombre/namejen "0.1.21"]

Then,

    (require '[namejen.names :as n])

    (n/gen-name-data-as-map)
    ;;=>
    {:gender :female
     :first-name "Luette"
     :prefix nil
     :surnames ["Adlai" "Angela" "Stlik"]
     :generation nil}

    ;; or
    (repeatedly 10 name-maker)
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
      "Mr. Nces Line Herbertran")

Several other functions in [the `namejen.names`
namespace](https://raw.githack.com/eigenhombre/namejen/master/docs/namejen.names.html)
may be of interest.

See [the API
docs](https://raw.githack.com/eigenhombre/namejen/master/docs/index.html)
for comprehensive info.

## Building

Build tasks are managed using `Make`.  You will need the following installed locally:

- make
- Java
- Leiningen
- NodeJS

`make` by itself or `make all` will perform all the build tasks
locally; or, you can build inside a Docker container using `make
docker`.

The build tasks include:

- jar file and überjar
- Clojure unit tests (Kaocha test runner)
- ClojureScript unit tests (in NodeJS via `lein doo`)
- linting with Bikeshed and Eastwood (Kibit disabled for now, because of lack of reader conditional support)
- API doc generation

This all may seem like overkill for a name generation library, but I
do want this to be fairly reusable and I'm trying to establish good
habits ;-).

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
