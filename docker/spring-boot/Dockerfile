# Use an official OpenJDK runtime as a parent image
FROM thingsboard/openjdk17:1.9.0

# Update package list and install jq and maven
RUN apt-get update && \
    apt-get install -y jq maven && \
    rm -rf /var/lib/apt/lists/*


COPY maven-settings.xml /usr/src/app/.m2/settings.xml

# Set the working directory in the container and create it with appropriate permissions
WORKDIR /usr/src/app
RUN mkdir -p /usr/src/app && \
    chmod -R 755 /usr/src/app

# Copy source code and pom.xml to the working directory
COPY ./src /usr/src/app/src/
COPY pom.xml /usr/src/app/
COPY run-sonar.sh /usr/src/app/run-sonar.sh

# Ensure the run script is executable
RUN chmod +x /usr/src/app/run-sonar.sh

# Download the OpenTelemetry Java agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.14.0/opentelemetry-javaagent.jar /usr/src/app/opentelemetry-javaagent.jar

# Environment variable for SonarQube token
ARG SONAR_TOKEN



# Copy the application's jar file to the container
COPY target/number-to-roman-0.0.1-SNAPSHOT.jar /usr/src/app/app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file with the OpenTelemetry Java agent
ENTRYPOINT ["java", "-javaagent:/usr/src/app/opentelemetry-javaagent.jar", "-jar", "/usr/src/app/app.jar"]
