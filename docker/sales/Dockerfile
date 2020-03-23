FROM openjdk:11-jre
COPY application.yml application.yml
COPY sales-0.0.1-SNAPSHOT.jar sales-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","sales-0.0.1-SNAPSHOT.jar","--spring.config.location=application.yml"]