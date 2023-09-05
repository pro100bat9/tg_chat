FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /build
COPY . .
RUN mvn clean install

FROM openjdk:17.0.2-jdk
RUN ls -l /build/target/
WORKDIR /java
COPY --from=build /build/target/*.jar /java/java.jar

CMD ["java", "-jar", "/java/java.jar"]
