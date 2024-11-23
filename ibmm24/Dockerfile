# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven-built application jar file from the target directory
# (Assumes the final jar is named based on your project artifactId and version in pom.xml)
COPY target/*.jar app.jar

# Expose the application port (e.g., 8080 for Spring Boot)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
