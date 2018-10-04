#!/bin/bash
docker build -t chat-room:latest ../
docker run --rm --name chat-room -p 8001:8080 -v /var/jenkins_home/logs:/var/log chat-room:latest
