FROM openjdk:11

ENV JAVA_OPTS " -Xms2G -Xmx2G"

EXPOSE 9010

ARG JAR_FILE=target/user-mod-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

#WORKDIR app

#COPY ./target/spr-dock-1.0-SNAPSHOT.jar ./

ENTRYPOINT ["java", "-jar", "app.jar"]