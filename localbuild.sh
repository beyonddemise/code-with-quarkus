#!/bin/bash
# run a local build
mvn -Pnative \
    --no-transfer-progress \
    --show-version \
    --fail-fast \
    --batch-mode \
    -DskipTests \
    -Dquarkus.container-image.push=false \
    clean package
