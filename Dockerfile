FROM java:8

MAINTAINER htg huangtg332052@163.com

ARG active_profile

RUN mkdir -p /apps/
WORKDIR /apps/
COPY  ./target/chat-room.war /apps/

EXPOSE 8080

CMD java -Dspring.profiles.active=$active_profile -jar chat-room.war > /var/log/chat-room.log