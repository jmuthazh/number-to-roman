#!/bin/bash

# Check if the container ID or name is provided
if [ -z "$1" ]; then
  echo "Usage: $0 <container_id_or_name>"
  exit 1
fi

CONTAINER=$1

# Fetch the logs of the specified container
docker-compose -f ../docker/docker-compose.yml logs $CONTAINER
