FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /build
COPY . .
RUN mvn clean package

FROM openjdk:17.0.2-jdk
ARG JAR_FILE=target/*.jar
WORKDIR /java
COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "/java/java.jar"]
