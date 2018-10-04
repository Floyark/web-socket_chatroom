pipeline {
    agent any
    stages {
        stage('Build') {
            agent {
                 docker {
                      image 'maven:3.5.2-jdk-8'
                      args '-v /root/.m2:/root/.m2'
                  }
            }
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deliver') {
            agent {
                node {
                    label 'delivery'
                    customWorkspace '/var/jenkins_home/workspace/websocket-chatroom'
                }
            }
            steps {
                sh 'docker build -t chat-room:latest .'
                sh '''
                    CONTAINER_ID=$(docker ps | grep chat-room | awk '{print $1}')
                    if [ "$CONTAINER_ID" ];then
                        docker stop $CONTAINER_ID
                    fi
                    CONTAINER_ID=$(docker ps -a | grep chat-room | awk '{print $1}')
                    if [ "$CONTAINER_ID" ];then
                        docker rm $CONTAINER_ID
                    fi
                '''
                sh 'docker run -dit --rm --name chat-room -p 8001:8080 -v /var/jenkins_home/logs:/var/log chat-room:latest &'
                sh '''
                    CONTAINER_ID=$(docker ps | grep chat-room | awk '{print $1}')
                    if [ "$CONTAINER_ID" ];then
                        docker start $CONTAINER_ID
                        [ $?>0 ] && echo '启动工程失败，需要手动！'
                    fi
                '''
            }
            options {
                retry(3)
            }
            post {
                success  {
                    echo '构建成功!'
                }
                failure {
                    echo '工程构建失败！'
                }
            }
        }
    }
}