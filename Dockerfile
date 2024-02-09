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
FROM tomcat:8-jre8-alpine
COPY --from=build /app/target/mos-cms-service-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/mos-cms-service.jar
EXPOSE 8900

# Cài đặt env-cmd
RUN apk add --no-cache nodejs npm
RUN npm install -g env-cmd

# Sao chép tệp .env và sử dụng env-cmd để đọc biến môi trường
COPY .env .env

CMD ["env-cmd", "mvn", "spring-boot:run"]
