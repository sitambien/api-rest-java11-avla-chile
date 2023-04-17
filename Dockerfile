FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/api-rest-avla-chile-0.0.1-SNAPSHOT.jar api-rest.jar
ENTRYPOINT ["java", "-jar", "/api-rest.jar"]