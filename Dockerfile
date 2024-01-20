# Use the official OpenJDK base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]
