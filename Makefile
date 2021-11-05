.PHONY: all test

all: test uberjar

target/uberjar/namejen.jar: src/namejen/*.clj resources/*.txt resources/*.edn
	lein uberjar

test:
	lein test

uberjar: target/uberjar/namejen.jar
