# Use OpenJDK for Spring
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy built JAR file
COPY target/*.jar app.jar

# Expose the port and run the app
EXPOSE 54080
ENTRYPOINT ["java", "-jar", "app.jar"]
