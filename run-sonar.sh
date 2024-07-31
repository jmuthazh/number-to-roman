#!/bin/bash

# Running SonarQube analysis
echo "Starting SonarQube analysis..."
sleep 5


# Run SonarQube analysis with exclusions
mvn clean verify sonar:sonar \
    -Dsonar.projectKey='number-to-roman' \
    -Dsonar.projectName='number-to-roman' \
    -Dsonar.host.url=http://sonarqube:9000 \
    -Dsonar.login=$SONAR_TOKEN \
    -Dsonar.exclusions='**/com/adobe/convertor/exception/**,**/com/adobe/convertor/bean/**'

