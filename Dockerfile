# Build stage
FROM maven:3.8.6-eclipse-temurin-19 AS build
COPY src /home/app/src
COPY pom.xml /home/app
ARG ARG_DATABASE_URL
ENV DATABASE_URL=$ARG_DATABASE_URL
RUN mvn -f /home/app/pom.xml clean test package


# Package stage
FROM openjdk:19-alpine3.14
COPY --from=build /home/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]