.PHONY: all test docker release doc cljs jar uberjar

all: test cljs cljs-test uberjar doc

cljs:
	lein cljsbuild once

cljs-test:
	lein doo node test once

test:
	lein do kaocha, bikeshed, kibit, eastwood

uberjar:
	lein uberjar

clean:
	rm -rf target docs

jar:
	lein jar

release:
	lein release

doc:
	lein codox

docker:
	docker build --progress tty -t namejen .
