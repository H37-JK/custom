# Use an official Java runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file from the build context into the container
# The JAR file name is passed as a build argument
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the JAR file when the container launches
ENTRYPOINT ["java", "-jar", "application.jar"]
