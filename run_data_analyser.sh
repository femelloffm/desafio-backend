#!/bin/bash

function getServerPort() {
    if [[ -v SERVER_PORT ]]; then
        echo $SERVER_PORT
    else
        echo 8080
    fi
}

function startContainer() {
    if [[ $(docker images | grep femelloffm/data-analyser) == "" ]]; then
        echo "------- Building Docker image femelloffm/data-analyser:1.0... -------"
        docker build -t femelloffm/data-analyser:1.0 .
    fi

    if [[ $(docker ps -a | grep data-analyser) == "" ]]; then
        echo "------- Running container data-analyser... -------"
        docker run --name data-analyser -p $(getServerPort):8080 -v $HOME/data/in/:/home/analyser/data/in \
        -v $HOME/data/out/:/home/analyser/data/out  -d femelloffm/data-analyser:1.0
    else
        echo "------- Starting container data-analyser... -------"
        docker start data-analyser
    fi
}

function stopContainer() {
    echo "------- Stopping data-analyser... -------"
    docker stop data-analyser
}

function statusContainer() {
    if [[ $(docker ps -a | grep data-analyser) == "" ]]; then
        echo "Container: NOT RUNNING"
    elif [[ $(docker inspect --format='{{.State.Running}}' data-analyser) == "true" ]]; then
        echo "Container: RUNNING"
        echo "Application: $(statusApplication)"
    else
        echo "Container: NOT RUNNING"
    fi
}

function statusApplication() {
    server_port=$(getServerPort)
    app_status=$(curl --write-out %{http_code} --silent --output /dev/null http://localhost:$server_port/health)
    if [[ $app_status == "200" ]]; then
        echo "RUNNING"
    else
        echo "NOT RUNNING"
    fi
}

if [[ -z $1 ]]; then
    echo "Missing argument. Use one of the following arguments: start | stop | status"
    exit 1
fi

case $1 in
    start)
        startContainer
        ;;
    stop)
        stopContainer
        ;;
    status)
        statusContainer
        ;;
    *)
        echo "Invalid argument. Supported arguments are: start | stop | status"
        ;;
esac