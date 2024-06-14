FROM amazoncorretto:17-alpine-jdk
MAINTAINER Jey
COPY target/GestionClienteNew-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java","-jar","/app.war"]
