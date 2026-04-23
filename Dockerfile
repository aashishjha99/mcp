# Stage 1: Build the application using Gradle
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy gradle wrapper and related files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Grant execution rights to the gradle wrapper
RUN chmod +x ./gradlew

# Download dependencies (this step is cached if build.gradle/settings.gradle don't change)
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the application source code
COPY src src

# Build the application
RUN ./gradlew clean bootJar --no-daemon -x test

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application (and strictly disable the docker compose internal hook as a fallback)
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.docker.compose.enabled=false"]