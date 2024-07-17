.PHONY: all test docker release doc cljs jar uberjar

all: test cljs-test cljs uberjar doc

cljs:
	lein cljsbuild once

cljs-test:
	lein doo node test once

test:
	lein do kaocha, bikeshed, eastwood   # kibit disabled for now, no reader conditionals....

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
	docker build -t namejen .
