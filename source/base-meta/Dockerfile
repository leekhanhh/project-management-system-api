FROM openjdk:11
#FROM openjdk:12-jdk-alpine

VOLUME /tmp
#WORKDIR /opt
COPY ./target/base-meta-1.0.0.jar app.jar
#COPY ./config.properties config.properties

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-DskipTests","-jar","/app.jar"]
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active={PROFILES_ACTIVE}"]
#ENTRYPOINT ["java", "-Xmx256m", "-Xss32m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]