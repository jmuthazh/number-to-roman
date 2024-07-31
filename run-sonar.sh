#!/bin/bash

# Wait for SonarQube to be available
while true; do
  # Fetch the health status from SonarQube
  RESPONSE=$(curl -s -u $SONAR_USER:$SONAR_PASSWORD http://sonarqube:9000/api/system/health)

  # Print the response for debugging
  echo "Response from SonarQube: $RESPONSE"

  # Check if the response contains the expected health status
  if echo "$RESPONSE" | grep -q '"health":"GREEN"'; then
    echo "SonarQube is up and running."
    break
  else
    echo "Waiting for SonarQube to be available..."
    sleep 5
  fi
done

# Run SonarQube analysis with exclusions
mvn clean verify sonar:sonar \
    -Dsonar.projectKey='number-to-roman' \
    -Dsonar.projectName='number-to-roman' \
    -Dsonar.host.url=http://sonarqube:9000 \
    -Dsonar.login=$SONAR_TOKEN \
    -Dsonar.exclusions='**/com/adobe/convertor/exception/**,**/com/adobe/convertor/bean/**'

