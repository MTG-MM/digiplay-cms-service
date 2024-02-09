# Build Stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
RUN apk update && apk add gcompat
WORKDIR /app
COPY pom.xml ./pom.xml
COPY src ./src
COPY .env .env
RUN mvn clean
RUN mvn install
RUN mvn package

RUN apk add --no-cache nodejs npm
RUN npm install -g env-cmd

RUN env-cmd
ENTRYPOINT ["java","-jar","/app/target/mos-cms-service-0.0.1-SNAPSHOT.jar"]
