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
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deliver') {
            steps {
                sh 'mvn spring-boot:run'
            }
            post {
                success  {
                    echo '构建成功!'
                }
            }
        }
    }
}