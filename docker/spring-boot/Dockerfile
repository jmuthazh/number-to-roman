# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Install Maven
RUN apk add maven

COPY maven-settings.xml /usr/src/app/.m2/settings.xml

# Set the working directory in the container and create it with appropriate permissions
WORKDIR /usr/src/app
RUN mkdir -p /usr/src/app && \
    chmod -R 755 /usr/src/app
COPY ./src /usr/src/app/src/
COPY pom.xml /usr/src/app/
# Download the OpenTelemetry Java agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.14.0/opentelemetry-javaagent.jar /usr/src/app/opentelemetry-javaagent.jar

# Copy the application's jar file to the container
COPY target/number-to-roman-0.0.1-SNAPSHOT.jar /usr/src/app/app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file with the OpenTelemetry Java agent
ENTRYPOINT ["java", "-javaagent:/usr/src/app/opentelemetry-javaagent.jar", "-jar", "/usr/src/app/app.jar"]
