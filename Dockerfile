# Build Stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
RUN apk update && apk add gcompat
WORKDIR /app
COPY pom.xml ./pom.xml
COPY src ./src
RUN mvn clean
RUN mvn install
RUN mvn package

# Runtime Stage
FROM tomcat:9.0.56-jre11-alpine
COPY --from=build /app/target/mos-cms-service.jar /usr/local/tomcat/webapps/mos-cms-service.jar
EXPOSE 8900
CMD ["catalina.sh", "run"]
