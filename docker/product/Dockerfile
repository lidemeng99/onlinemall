FROM openjdk:11-jre
COPY application.yml application.yml
COPY product-0.0.1-SNAPSHOT.jar product-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","product-0.0.1-SNAPSHOT.jar","--spring.config.location=application.yml"]