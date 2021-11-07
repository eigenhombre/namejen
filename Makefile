.PHONY: all test docker release doc cljs

all: test cljs uberjar doc

target/uberjar/namejen.jar: src/clj/namejen/*.clj src/cljs/namejen/*.cljs test/namejen/*.clj resources/*.txt
	lein uberjar

cljs:
	lein cljsbuild once

test:
	lein do kaocha, bikeshed, kibit, eastwood

uberjar: target/uberjar/namejen.jar

release:
	lein release

doc:
	lein codox

docker:
	docker build --progress tty -t namejen .
