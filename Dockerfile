FROM openjdk:11-jre-slim-buster

COPY target/letmeknow-0.0.1-SNAPSHOT.jar letmeknow-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/letmeknow-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080