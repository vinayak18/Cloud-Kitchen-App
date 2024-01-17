FROM openjdk:17-alpine

EXPOSE 8086

COPY target/apigateway-0.0.1-SNAPSHOT.jar apigateway.jar

ENTRYPOINT ["java","-jar","apigateway.jar"]