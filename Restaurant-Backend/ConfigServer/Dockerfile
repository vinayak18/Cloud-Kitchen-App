FROM openjdk:17-alpine

EXPOSE 8087

COPY target/config.server-0.0.1-SNAPSHOT.jar cloudconfigserver.jar

ENTRYPOINT ["java","-jar","cloudconfigserver.jar"]