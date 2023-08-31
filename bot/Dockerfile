FROM openjdk:17.0.2-jdk-slim
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java -jar /app.jar
