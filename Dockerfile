# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application source code and Gradle files into the container
COPY . .

# Build the application using the Gradle Wrapper
RUN ./gradlew build

# Expose the port that your application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["./gradlew", "bootRun"]
