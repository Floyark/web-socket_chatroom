FROM jboss/base-jdk:8

MAINTAINER htg huangtg332052@163.com

RUN mkdir ~/apps/chat-room
COPY ./target/chat-room.war ~/apps/

EXPOSE 8080

CMD java -jar ~/apps/chat-room.war > /var/log/chat-room.log &