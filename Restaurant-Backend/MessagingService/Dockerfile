FROM openjdk:17-alpine

EXPOSE 8085

COPY target/messagingservice-0.0.1-SNAPSHOT.jar messagingservice.jar

ENTRYPOINT ["java","-jar","messagingservice.jar"]