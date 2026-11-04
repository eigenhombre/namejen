# Releasing

The current version lives in `VERSION`.  `make release` drops the
`-SNAPSHOT` suffix, updates the version in `README.md`, commits, tags,
deploys a GPG-signed jar to Clojars, bumps to the next `-SNAPSHOT`
version, commits again, and pushes.

It requires a clean working tree and the `CLOJARS_USERNAME` and
`CLOJARS_PASSWORD` (deploy token) environment variables:

    make release
