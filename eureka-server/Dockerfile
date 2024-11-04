# Build stage
FROM gradle:8.10.2-jdk17-alpine AS build
WORKDIR /app

# Copy the Gradle configuration files separately to leverage Docker cache
COPY gradlew gradlew.bat /app/
RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew
COPY gradle /app/gradle

# Grant execute permissions to the Gradle wrapper
RUN chmod +x ./gradlew

# Pre-download dependencies to cache them
COPY build.gradle settings.gradle /app/
RUN ./gradlew --no-daemon dependencies

# Copy the rest of the project
COPY src /app/src

# Build the application without running tests
RUN ./gradlew assemble --no-daemon -x test

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8761

# Command to run the application
CMD ["java", "-jar", "app.jar"]
