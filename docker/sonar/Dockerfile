FROM sonarqube:latest

# Copy the script into the container
COPY generate-token.sh /opt/generate-token.sh

# Give execute permission to the script
RUN chmod +x /opt/generate-token.sh

# Run the script and then start SonarQube
ENTRYPOINT ["/bin/bash", "-c", "/opt/generate-token.sh && ./bin/run.sh"]
