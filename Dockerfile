# Use OpenJDK for Spring
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy built JAR file
COPY build/libs/*.jar app.jar
# Gradle로 빌드된 JAR 파일을 복사

# Expose the port and run the app
EXPOSE 54080
ENTRYPOINT ["java", "-jar", "app.jar"]
