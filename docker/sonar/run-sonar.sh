#!/bin/bash

TOKEN_NAME=$(uuidgen)
# Wait for SonarQube to be up
echo "Waiting for SonarQube to start..."
if curl -u admin:Sonar@123 http://sonarqube:9000/api/system/health | grep -q '"health":"GREEN"';then
  echo "SonarQube is up."
  curl -u admin:Sonar@123 http://sonarqube:9000/api/system/health  # Print status for debugging
  echo "Generating SonarQube token..."
  SONAR_TOKEN=$(curl -u admin:Sonar@123 -X POST "http://sonarqube:9000/api/user_tokens/generate?name=$TOKEN_NAME" | grep -oP '"token":\s*"\K[^"]+')

  # Check if token was successfully generated
  if [ -z "$SONAR_TOKEN" ]; then
    echo "Failed to generate SonarQube token. Exiting..."
    exit 1
  fi


else




# Check if password has already been set
if curl -u admin:admin -s -o /dev/null -w "%{http_code}" http://sonarqube:9000/api/users/search?login=admin | grep -q "200"; then
  echo "Admin user already exists. Skipping password change."
fi

# Set the default admin password
curl -u admin:admin -X POST "http://sonarqube:9000/api/users/change_password?login=admin&previousPassword=admin&password=Sonar@123"
# Generate SonarQube token
echo "Generating SonarQube token..."
SONAR_TOKEN=$(curl -u admin:Sonar@123 -X POST "http://sonarqube:9000/api/user_tokens/generate?name=$TOKEN_NAME" | grep -oP '"token":\s*"\K[^"]+')

# Check if token was successfully generated
if [ -z "$SONAR_TOKEN" ]; then
  echo "Failed to generate SonarQube token. Exiting..."
  exit 1
fi
fi

# Running SonarQube analysis
echo "Starting SonarQube analysis..."
sleep 5


# Run SonarQube analysis with exclusions
mvn clean verify sonar:sonar \
    -Dsonar.projectKey='number-to-roman' \
    -Dsonar.projectName='number-to-roman' \
    -Dsonar.host.url=http://sonarqube:9000 \
    -Dsonar.token=$SONAR_TOKEN \
    -Dsonar.exclusions='**/com/adobe/convertor/exception/**,**/com/adobe/convertor/bean/**,**com/adobe/convertor/NumberToRomanApplication.java,**com/adobe/convertor/service/NumberToRomanService.java' \
    -Dsonar.scm.disabled=true

