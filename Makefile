.PHONY: all test docker release doc

all: test uberjar doc

target/uberjar/namejen.jar: src/namejen/*.clj resources/*.txt
	lein uberjar

test:
	lein do kaocha, bikeshed, kibit, eastwood

uberjar: target/uberjar/namejen.jar

release:
	lein release

doc:
	lein codox

docker:
	docker build --progress tty -t namejen .
