# Use a base image
FROM maven:3.8.4-openjdk-17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/theagenda-1.0.5.jar .

EXPOSE 9090

# Run the application
CMD ["java", "-jar", "theagenda-1.0.5.jar"]
