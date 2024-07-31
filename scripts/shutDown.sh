#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# Print each command before executing it (useful for debugging)
set -x

#restart docker-compose
docker-compose -f ../docker/docker-compose.yml down --remove-orphans
