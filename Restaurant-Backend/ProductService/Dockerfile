FROM openjdk:17-alpine

EXPOSE 8082

COPY target/productservice-0.0.1-SNAPSHOT.jar productservice.jar

ENTRYPOINT ["java","-jar","productservice.jar"]