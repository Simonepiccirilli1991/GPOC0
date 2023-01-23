FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/GPOC0-0.0.1-SNAPSHOT.jar /app/GPOC0.jar

# Expose the port
EXPOSE 8085

LABEL name="gpoc-img"

# Run the application
CMD ["java", "-jar", "GPOC0.jar"]
