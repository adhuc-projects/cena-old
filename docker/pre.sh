#!/bin/bash

# Start applicative containers

MAX_WAIT_TIME=$1
PORT=$2

cd .. && make up

# Wait for application to startup
count=0
until $(curl --output /dev/null --silent --head --fail http://localhost:$PORT/api/management/health) || ((count == MAX_WAIT_TIME)); do
   printf '.'
   ((count++))
   sleep 1
done

if ((count < MAX_WAIT_TIME))
then
    printf "\nhealth responded in $count seconds\n"
else
    printf "\nfailed to start under $MAX_WAIT_TIME seconds\n"
    make down
    exit 1
fi
