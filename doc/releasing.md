# Releasing

The current version lives in `VERSION`.  `make release` drops the
`-SNAPSHOT` suffix, updates the version in `README.md`, commits, tags,
deploys a GPG-signed jar to Clojars, bumps to the next `-SNAPSHOT`
version, commits again, and pushes.

It requires a clean working tree and Clojars credentials in
`CLOJARS_USERNAME` and `CLOJARS_PASSWORD` (a deploy token, not your
account password).  `make release` refuses to start if either is
missing:

    export CLOJARS_USERNAME=eigenhombre
    export CLOJARS_PASSWORD="$CLOJARS_LAPTOP_DEPLOY_TOKEN"
    make release
