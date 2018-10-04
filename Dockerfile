ARG ACTIVE_PROFILE
FROM java:8

MAINTAINER htg huangtg332052@163.com

RUN mkdir -p /apps/
WORKDIR /apps/
COPY  ./target/chat-room.war /apps/

EXPOSE 8080

CMD java -Dspring.profiles.active=$ACTIVE_PROFILE -jar chat-room.war > /var/log/chat-room.log