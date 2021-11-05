.PHONY: all

target/uberjar/namejen.jar: src/namejen/*.clj resources/*.txt resources/*.edn
	lein uberjar

all:
	make uberjar
