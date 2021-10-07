FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT java ${JAVA_OPTS} -Xmx512m -Xms512m -Djava.security.egd=file:/dev/./urandom -Duser.timezone=GMT+8 -jar /app.jar

# -Djava.security.egd=file:/dev/./urandom 为 Random 随机数优化方案
# -Duser.timezone=GMT+8 为解决线上环境 JVM 时区问题
# -XX:MetaspaceSize=128m （元空间默认大小）
# -XX:MaxMetaspaceSize=128m （元空间最大大小）
# -Xms1024m （堆最大大小）
# -Xmx1024m （堆默认大小）
# -Xmn256m （新生代大小）
# -Xss256k （棧最大深度大小）
# -XX:SurvivorRatio=8 （新生代分区比例 8:2）
# -XX:+UseConcMarkSweepGC （指定使用的垃圾收集器，这里使用CMS收集器）
# -XX:+PrintGCDetails （打印详细的GC日志）
