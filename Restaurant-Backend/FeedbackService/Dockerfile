FROM openjdk:17-alpine

EXPOSE 8084

COPY target/feedbackservice-0.0.1-SNAPSHOT.jar feedbackservice.jar

ENTRYPOINT ["java","-jar","feedbackservice.jar"]