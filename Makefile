BINDIR ?= $(HOME)/bin
BUILD = clojure -T:build:cljs

.PHONY: all
all: test cljs-test cljs uberjar doc

.PHONY: cljs
cljs:
	$(BUILD) cljs

.PHONY: cljs-test
cljs-test:
	clojure -M:cljs:cljs-test

.PHONY: test
test:
	clojure -M:test
	clojure -M:lint

.PHONY: uberjar
uberjar:
	$(BUILD) uber

.PHONY: clean
clean:
	rm -rf target doc/codox

.PHONY: jar
jar:
	$(BUILD) jar

.PHONY: release
release:
	$(BUILD) release

.PHONY: doc
doc:
	rm -rf doc/codox
	clojure -X:codox :version '"$(shell cat VERSION)"'

.PHONY: docker
docker:
	docker build -t namejen .

.PHONY: install
install: uberjar
	mkdir -p $(BINDIR)
	cp target/namejen-*-standalone.jar $(BINDIR)/namejen.jar
	cp namejen $(BINDIR)/namejen
