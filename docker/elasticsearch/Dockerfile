# Use the official Elasticsearch image as a base
FROM docker.elastic.co/elasticsearch/elasticsearch:7.13.4

# Set environment variables for Elasticsearch
ENV discovery.type=single-node
# Adjust heap size as needed
ENV ES_JAVA_OPTS="-Xms1g -Xmx1g"

# Copy custom Elasticsearch configuration file
COPY elasticsearch.yml /usr/share/elasticsearch/config/elasticsearch.yml

# Expose ports for Elasticsearch
EXPOSE 9200 9300

# Run Elasticsearch
CMD ["elasticsearch"]
