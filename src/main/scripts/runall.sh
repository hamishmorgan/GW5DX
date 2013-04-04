#!/bin/bash

# ===============================================================================
# Script that runs the Gigaword 5 gram-relation extractor on a number of input
# files. Forking a separate process for each file, and merge the result at the end.
#
# Author: Hamish Morgan
# ===============================================================================

set -o errexit
set -o monitor
#set -o nounset
set -o errtrace


readonly OUTPUT_DIR="output"
readonly OUTPUT_SUFFIX=".gramrels.tsv"
readonly STDOUT_SUFFIX=".stdout.txt"
readonly STDERR_SUFFIX=".stdout.txt"
readonly TEMP_DIR=`mktemp -d "${OUTPUT_DIR}/tmp.XXXXXXXX"`
readonly COMBINED_OUTPUT_FILE="${OUTPUT_DIR}/output.tsv"

readonly SGE_OPTIONS="-cwd -q nlp-amd,serial.q,dragon.q,inf.q,nlp.q -j yes"


function cleanup() {
    echo "Cleaning up temporary files."
    rm -r "${TEMP_DIR}"
}


function runSerial() {

    declare -a OUTPUT_FILES=();
    for FILE in "$@"; do
        FILENAME=`basename "${FILE}"`
        OUTPUT_FILE="${TEMP_DIR}/${FILENAME}${OUTPUT_SUFFIX}"
        STDOUT_FILE="${TEMP_DIR}/${FILENAME}${STDOUT_SUFFIX}"
        STDERR_FILE="${TEMP_DIR}/${FILENAME}${STDERR_SUFFIX}"

        echo "Running GW5GR on file: ${FILE}..."
        if [[ "${STDOUT_SUFFIX}" == "${STDERR_SUFFIX}" ]]; then
            ./gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" > "${STDOUT_FILE}" 2>&1
        else
            ./gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" > "${STDOUT_FILE}" 2> "${STDERR_SUFFIX}"
        fi
        echo "Completed ${FILE}."
        OUTPUT_FILES=("${OUTPUT_FILES[@]}" "${OUTPUT_FILE}")
    #    sleep 1 # Don't let it burn through jobs too quickly
    done

    echo "Merging results"
    cat "${OUTPUT_FILES[@]}" | gzip > ${COMBINED_OUTPUT_FILE}.gz
    cleanup
}


function runParallel() {
    declare -a JOB_PIDS=();
    declare -a JOB_DESCRIPTIONS=();
    declare -a OUTPUT_FILES=();

    for FILE in "$@"; do
        FILENAME=`basename "${FILE}"`
        OUTPUT_FILE="${TEMP_DIR}/${FILENAME}${OUTPUT_SUFFIX}"
        STDOUT_FILE="${TEMP_DIR}/${FILENAME}${STDOUT_SUFFIX}"
        STDERR_FILE="${TEMP_DIR}/${FILENAME}${STDERR_SUFFIX}"

        if [[ "${STDOUT_SUFFIX}" == "${STDERR_SUFFIX}" ]]; then
            ./gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" > "${STDOUT_FILE}" 2>&1 &
        else
            ./gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" > "${STDOUT_FILE}" 2> "${STDERR_SUFFIX}" &
        fi

        JOB_PIDS=("${JOB_PIDS[@]}" "$!")
        JOB_DESCRIPTIONS=("${JOB_DESCRIPTIONS[@]}" "${FILE} [PID: $!]")
        OUTPUT_FILES=("${OUTPUT_FILES[@]}" "${OUTPUT_FILE}")

        echo "Running GW5GR on file: ${FILE} [PID: $!]..."
    #    sleep 1 # Don't let it burn through jobs too quickly
    done

    echo "Waiting for all jobs to complete...."

    for (( i = 0 ; i < ${#JOB_DESCRIPTIONS[@]} ; i++ )) do
        wait ${JOB_PIDS[$i]}
        echo "Completed ${JOB_DESCRIPTIONS[$i]}."
    done

    echo "Merging results"
    cat "${OUTPUT_FILES[@]}" | gzip > ${COMBINED_OUTPUT_FILE}.gz
    cleanup
}

#function qsub() {
#    echo "qsub $@"
#}

function sgeSubmit() {
    declare -a OUTPUT_FILES=();
    for FILE in "$@"; do
        FILENAME=`basename "${FILE}"`
        OUTPUT_FILE="${TEMP_DIR}/${FILENAME}${OUTPUT_SUFFIX}"
        STDOUT_FILE="${TEMP_DIR}/${FILENAME}${STDOUT_SUFFIX}"
        STDERR_FILE="${TEMP_DIR}/${FILENAME}${STDERR_SUFFIX}"

        echo "Submitting GW5GR on file: ${FILE}..."
        if [[ "${STDOUT_SUFFIX}" == "${STDERR_SUFFIX}" ]]; then
            qsub ${SGE_OPTIONS} gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" # > "${STDOUT_FILE}" 2>&1
        else
            qsub ${SGE_OPTIONS} gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" # > "${STDOUT_FILE}" 2> "${STDERR_SUFFIX}"
        fi
        OUTPUT_FILES=("${OUTPUT_FILES[@]}" "${OUTPUT_FILE}")
    #    sleep 1 # Don't let it burn through jobs too quickly
    done

#    echo "Merging results"
#    cat "${OUTPUT_FILES[@]}" | gzip > ${COMBINED_OUTPUT_FILE}.gz
}


function sgeArraySubmit() {


    declare -a OUTPUT_FILES=();
    for FILE in "$@"; do
        FILENAME=`basename "${FILE}"`
        OUTPUT_FILE="${TEMP_DIR}/${FILENAME}${OUTPUT_SUFFIX}"
        STDOUT_FILE="${TEMP_DIR}/${FILENAME}${STDOUT_SUFFIX}"
        STDERR_FILE="${TEMP_DIR}/${FILENAME}${STDERR_SUFFIX}"

        echo "Submitting GW5GR on file: ${FILE}..."
        if [[ "${STDOUT_SUFFIX}" == "${STDERR_SUFFIX}" ]]; then
            qsub ${SGE_OPTIONS} gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" # > "${STDOUT_FILE}" 2>&1
        else
            qsub ${SGE_OPTIONS} gw5gr.sh -o "${OUTPUT_FILE}" "${FILE}" # > "${STDOUT_FILE}" 2> "${STDERR_SUFFIX}"
        fi
        OUTPUT_FILES=("${OUTPUT_FILES[@]}" "${OUTPUT_FILE}")
    #    sleep 1 # Don't let it burn through jobs too quickly
    done

#    echo "Merging results"
#    cat "${OUTPUT_FILES[@]}" | gzip > ${COMBINED_OUTPUT_FILE}.gz
}


#runParallel $@
#runSerial $@
sgeSubmit $@




echo "All done."
exit 0

