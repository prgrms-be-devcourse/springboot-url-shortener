FROM openjdk:17-jdk
LABEL maintainer="Yosongsong"

ARG JAR_FILE=build/libs/springboot-url-shortener-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} url-shortener-springboot.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/url-shortener-springboot.jar"]
