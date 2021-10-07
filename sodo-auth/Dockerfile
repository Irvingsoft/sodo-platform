FROM openjdk:8-jdk-alpine
FROM frolvlad/alpine-java:jdk8-slim
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT java ${JAVA_OPTS} -Xmx512m -Xms512m -Djava.security.egd=file:/dev/./urandom -Duser.timezone=GMT+8 -jar /app.jar

# FROM frolvlad/alpine-java:jdk8-slim 解决通过 RUN apk add --update font-adobe-100dpi ttf-dejavu fontconfig 安装字体慢的问题