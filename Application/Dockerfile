#
# Build stage
#
FROM maven:4.0.2-openjdk-25 AS build
COPY . .
RUN mvn clean install

#
# Package stage
#
FROM eclipse-temurin:25-jdk
COPY --from=build /target/Application-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]