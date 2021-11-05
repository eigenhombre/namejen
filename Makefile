.PHONY: all test docker

all: test uberjar

target/uberjar/namejen.jar: src/namejen/*.clj resources/*.txt resources/*.edn
	lein uberjar

test:
	lein kaocha

uberjar: target/uberjar/namejen.jar

docker:
	docker build --progress tty -t namejen .
