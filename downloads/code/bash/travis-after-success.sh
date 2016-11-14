#!/usr/bin/env bash

if [ "${TRAVIS_PULL_REQUEST_BRANCH:-$TRAVIS_BRANCH}" != "master" ] && [ "$TRAVIS_RUST_VERSION" == "nightly" ]; then
    REMOTE_URL="$(git config --get remote.origin.url)";
    # Clone the repository fresh..for some reason checking out master fails
    # from a normal PR build's provided directory
    cd ${TRAVIS_BUILD_DIR}/.. && \
    git clone ${REMOTE_URL} "${TRAVIS_REPO_SLUG}-bench" && \
    cd  "${TRAVIS_REPO_SLUG}-bench" && \
    # Bench master
    git checkout master && \
    cargo bench > benches-control && \
    # Bench variable
    git checkout ${TRAVIS_COMMIT} && \
    cargo bench > benches-variable && \
    cargo install cargo-benchcmp --force && \
    cargo benchcmp benches-control benches-variable;
fi
