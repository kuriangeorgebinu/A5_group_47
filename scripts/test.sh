#!/usr/bin/env bash

SCENARIO=$1
DIRECTORY="./docker_results"
COMMAND="commands_${SCENARIO}.txt"
RESULTS="drone_delivery_${SCENARIO}_results.txt"
INITIAL="drone_delivery_initial_${SCENARIO}_results.txt"
DIFFERENCE="diff_results_${SCENARIO}.txt"

mkdir -p ${DIRECTORY}
docker run -e CLI=true --name dronedelivery java-a5 sh -c "\
    java -jar drone_delivery.jar < ${COMMAND} > ${RESULTS} && \
    diff -s ${RESULTS} ${INITIAL} > ${DIFFERENCE}" # && \
# cat ${DIFFERENCE}"
status=$?
docker cp dronedelivery:/usr/src/cs6310/${RESULTS} ${DIRECTORY}
docker cp dronedelivery:/usr/src/cs6310/${DIFFERENCE} ${DIRECTORY}
docker rm dronedelivery >/dev/null

FILE_CONTENTS="${DIRECTORY}/${DIFFERENCE}"
echo "$(cat ${FILE_CONTENTS})"
exit $status
