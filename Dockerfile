FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ ./src/

RUN mvn clean install

FROM eclipse-temurin:17-jdk-alpine

RUN mkdir /app

COPY --from=build /app/target/*.jar /app/app.jar

ENV SERVER_PORT=6060

WORKDIR /app

EXPOSE 6060

ENTRYPOINT ["java","-jar","/app/app.jar"]