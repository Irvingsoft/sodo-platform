FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 9501
ENTRYPOINT {"java", "-jar", "/app.jar"}