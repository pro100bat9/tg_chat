FROM maven:3.8.6-openjdk-18 AS build

WORKDIR /build
COPY . .
RUN mvn clean package

FROM openjdk:18.0.2-jdk

WORKDIR /java
COPY --from=build /build/target/*.jar /java/java.jar

CMD ["java", "-jar", "/java/java.jar"]
