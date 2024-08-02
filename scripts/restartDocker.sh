#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# Print each command before executing it (useful for debugging)
set -x

# docker-compose down
docker-compose -f ../docker/docker-compose.yml down

# remove all src/ run-sonar.sh, so that it copies the file during build
docker volume rm docker_sonar-data

# docker-compose up & build
docker-compose -f ../docker/docker-compose.yml up -d --build

# List all Docker Compose services
docker-compose -f ../docker/docker-compose.yml ps -a