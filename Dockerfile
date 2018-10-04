FROM openjdk:8

MAINTAINER htg huangtg332052@163.com

RUN mkdir -p /apps/
COPY ./target/chat-room.war /apps/

EXPOSE 8080

CMD java -jar ~/apps/chat-room.war > /var/log/chat-room.log &