FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/gateway-server-0.0.1-SNAPSHOT.jar app.jar
ENV EUREKA_URL null
ENTRYPOINT ["java","-jar","/app.jar"]