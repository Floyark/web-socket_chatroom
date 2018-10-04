ARG SPRING_PROFILE=qa
FROM java:8

MAINTAINER htg huangtg332052@163.com

cmd_line="java -Dspring.profiles.active=$SPRING_PROFILE -jar chat-room.war > /var/log/chat-room.log"
RUN mkdir -p /apps/
WORKDIR /apps/
COPY  ./target/chat-room.war /apps/

EXPOSE 8080

CMD ["/bin/sh","-c","$cmd_line"]