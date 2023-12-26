# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files for gradle build
COPY build.gradle.kts settings.gradle.kts ./
COPY gradlew ./
COPY gradle ./gradle

# Download dependencies to cache them in a separate layer
RUN ./gradlew build --no-daemon

# Copy the application source code
COPY . .

# Build the application
RUN ./gradlew build

# Expose the port that your application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["./gradlew", "bootRun"]
