FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT java ${JAVA_OPTS} -Xmx512m -Xms512m -Djava.security.egd=file:/dev/./urandom -Duser.timezone=GMT+8 -jar /app.jar