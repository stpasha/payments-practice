FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/payment-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5000", "-jar", "app.jar"]