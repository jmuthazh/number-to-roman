# Use an official Maven image to perform the build
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory
WORKDIR /usr/src/app

# Copy the pom.xml and project files
COPY ../../pom.xml .
COPY ../../src ./src

# Install Maven dependencies
RUN mvn dependency:go-offline

# Set environment variables for SonarQube
ENV SONAR_HOST_URL=http://localhost:9000
ENV SONAR_LOGIN=admin
ENV SONAR_PASSWORD=admin

# Run the SonarQube analysis
CMD ["mvn", "sonar:sonar", "-Dsonar.projectKey=number-to-roman-app", "-Dsonar.login=admin", "-Dsonar.password=admin"]
