ARG profiles=qa
FROM java:8

MAINTAINER htg huangtg332052@163.com

ARG profile

RUN mkdir -p /apps/
WORKDIR /apps/
COPY  ./target/chat-room.war /apps/

EXPOSE 8080

CMD java -Dspring.profiles.active=$profiles -jar chat-room.war > /var/log/chat-room.log