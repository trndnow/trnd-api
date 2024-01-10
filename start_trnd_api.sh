#!/bin/bash

# Path to the Spring Boot JAR file
JAR_PATH="trnd-api-0.0.1-SNAPSHOT.jar"

# Profile to activate
PROFILE="dev"

# Location of the external configuration file
CONFIG_LOCATION="/home/ubuntu/trnd/application-dev.properties"

LOG_FILE="application.log"

# Command to run the Spring Boot application
nohup java -jar $JAR_PATH --spring.profiles.active=$PROFILE --spring.config.location=file:$CONFIG_LOCATION > $LOG_FILE 2>&1 &

# Print the process ID
echo "Application started with PID $!"