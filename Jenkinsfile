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
            agent none
            steps {
                sh 'docker build -t chat-room:latest .'
                sh 'docker run -dit --rm --name chat-room -p 8001:8080 -v /var/jenkins_home/logs:/var/log chat-room:latest'
            }
            post {
                success  {
                    echo '构建成功!'
                }
            }
        }
    }
}