FROM openjdk:17-alpine

EXPOSE 8083

COPY target/userservice-0.0.1-SNAPSHOT.jar userservice.jar

ENTRYPOINT ["java","-jar","userservice.jar"]