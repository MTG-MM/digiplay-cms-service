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

RUN mkdir -p /usr/local/newrelic
ADD ./newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

RUN apk add --no-cache nodejs npm
RUN npm install -g env-cmd

CMD ["env-cmd", "-f", ".env"]
ENTRYPOINT ["java","-javaagent:/usr/local/newrelic/newrelic.jar","-jar","/app/target/mos-cms-service-0.0.1-SNAPSHOT.jar"]
