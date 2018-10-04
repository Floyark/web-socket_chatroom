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
                sh 'chmod 755 ./deploy/deploy.sh'
                sh './deploy/deploy.sh'
            }
            post {
                success  {
                    echo '构建成功!'
                }
            }
        }
    }
}