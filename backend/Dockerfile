# Use an official Java 8 runtime as the base image
FROM openjdk:8-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle project files to the container
COPY build.gradle .
COPY gradlew .
COPY gradle ./gradle
COPY src ./src
COPY build/libs/backend-0.0.1-SNAPSHOT.jar .

# Run Gradle Wrapper to download Gradle and build the application
RUN ./gradlew build

# Expose the port that the Spring Boot application uses
EXPOSE 8080

# Specify the command to run the Spring Boot application
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
