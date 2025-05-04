# Dockerfile
FROM openjdk:17-jdk-slim
COPY target/consumer-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-cp", "app.jar", "org.rp.common.kafka.Consumer"]