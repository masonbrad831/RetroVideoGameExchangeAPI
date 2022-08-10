FROM tomcat:8-jdk17-openjdk


ENV DB_HOST csc380mysql
ENV DB_PORT 3306
ENV DB_USER root
ENV DB_PASS testtest1

RUN apt-get install procps

COPY RetroVideoGameExchange-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/games.war