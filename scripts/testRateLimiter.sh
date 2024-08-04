#!/bin/bash

# Number of requests to send
NUM_REQUESTS=110

# URL to test
URL="http://localhost:8000/romannumeral?query=100"

# Authentication credentials
USERNAME="admin"
PASSWORD="SuperSecretPass123"

# Loop to send requests
for i in $(seq 1 $NUM_REQUESTS); do
  echo "Sending request #$i..."
  RESPONSE=$(curl -s -u $USERNAME:$PASSWORD -o /dev/null -w "%{http_code}" $URL)
  echo "Response status code: $RESPONSE"

done

echo "Test completed."

