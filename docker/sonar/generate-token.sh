#!/bin/bash

# Wait for SonarQube to be fully up and running
echo "Waiting for SonarQube to start..."
while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://localhost:9000/api/system/status)" != "200" ]]; do
  sleep 5
done

# Generate token using SonarQube API
echo "SonarQube is up and running. Generating token..."

# Admin credentials
ADMIN_USER="admin"
ADMIN_PASSWORD="admin"

# Login and get authentication token (CSRF token)
CSRF_TOKEN=$(curl -u $ADMIN_USER:$ADMIN_PASSWORD -X POST "http://localhost:9000/api/authentication/login" | grep -oP '(?<="csrfToken":")[^"]*')

# Generate a new token
TOKEN_RESPONSE=$(curl -u $ADMIN_USER:$ADMIN_PASSWORD -X POST "http://localhost:9000/api/user_tokens/generate?name=number-to-roman-app-token")

# Extract the token from the response
TOKEN=$(echo $TOKEN_RESPONSE | grep -oP '(?<="token":")[^"]*')

echo "Generated SonarQube token: $TOKEN"

# Optionally, save the token to a file or an environment variable
echo $TOKEN > /opt/sonarqube_token.txt

# Optionally, update sonar-project.properties or set the token as an environment variable
export SONAR_LOGIN=$TOKEN
