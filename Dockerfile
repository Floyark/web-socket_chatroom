ARG SPRING_PROFILE=qa
FROM java:8

MAINTAINER htg huangtg332052@163.com

ENV profile $SPRING_PROFILE

RUN mkdir -p /apps/
WORKDIR /apps/
COPY  ./target/chat-room.war /apps/

EXPOSE 8080

CMD ["/bin/sh","-c","java","-Dspring.profiles.active=$profile","-jar","chat-room.war > /var/log/chat-room.log"]