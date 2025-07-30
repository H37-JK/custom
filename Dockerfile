FROM eclipse-temurin:21-jdk

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar

EXPOSE 8080

# Run the JAR file when the container launches
ENTRYPOINT ["java", "-jar", "application.jar"]
