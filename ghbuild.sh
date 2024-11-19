#!/bin/bash
# run a build on github

version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
if [[ "$BRANCH_NAME" == "main" ]]; then
    tag="latest"
else
    tag="$BRANCH_NAME"
fi

mvn -Pnative \
    --no-transfer-progress \
    --show-version \
    --fail-fast \
    --batch-mode \
    -DskipTests \
    -Dquarkus.container-image.push=true \
    -Dquarkus.container-image.tag=${tag} \
    -Dquarkus.container-image.additional-tags=${version} \
    -Dquarkus.jib.platforms=$1 \
    clean package
