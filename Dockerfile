# Use a base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/theagenda-1.0.0.jar .

# Run the application
CMD ["java", "-jar", "theagenda-1.0.0.jar"]
