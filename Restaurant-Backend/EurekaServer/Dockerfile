FROM openjdk:17-alpine

EXPOSE 8761

COPY target/eurekaserver-0.0.1-SNAPSHOT.jar eurekaserver.jar

ENTRYPOINT ["java","-jar","eurekaserver.jar"]