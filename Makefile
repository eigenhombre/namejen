.PHONY: all test docker release

all: test uberjar

target/uberjar/namejen.jar: src/namejen/*.clj resources/*.txt resources/*.edn
	lein uberjar

test:
	lein do kaocha, bikeshed, kibit, eastwood

uberjar: target/uberjar/namejen.jar

release:
	lein release

docker:
	docker build --progress tty -t namejen .
