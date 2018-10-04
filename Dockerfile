FROM jboss/base-jdk:8

MAINTAINER htg huangtg332052@163.com

RUN mkdir /apps
CP /var/jenkins_home/workspace/websocket-chatroom/target/chat-room.war /apps

EXPOSE 8080

CMD java -jar /apps/chat-room.war > /var/log/chat-room.log &