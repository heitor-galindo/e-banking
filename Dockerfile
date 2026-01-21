# syntax=docker/dockerfile:1.7

FROM maven:3.9-eclipse-temurin-21 AS builder

ARG PROJECT_DIR
WORKDIR /app

COPY ${PROJECT_DIR}/pom.xml ./pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -B

COPY ${PROJECT_DIR}/src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
