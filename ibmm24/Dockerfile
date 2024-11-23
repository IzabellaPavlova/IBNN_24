# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Package the application (skip tests for faster builds in CI/CD pipelines if necessary)
RUN mvn package -DskipTests

# Stage 2: Create a minimal runtime image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
