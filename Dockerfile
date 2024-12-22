
FROM openjdk:17-oracle

WORKDIR /app

COPY target/notifications-service-0.0.1-SNAPSHOT.jar notifications-service.jar

CMD ["java","-jar","notifications-service.jar"]

