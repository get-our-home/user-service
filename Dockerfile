FROM openjdk:17-jdk

WORKDIR /app

COPY build/libs/*.jar user-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "user-service.jar"]
