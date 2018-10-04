pipeline {
    agent {
        docker {
            image 'maven:3.5.2-jdk-8'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deliver') {
            steps {
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