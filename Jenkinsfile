node {
    docker.image('maven:3.5.2-jdk-8').inside {
        try {
            stage ('Build') {
                sh 'mvn clean package'
            }

            stage ('Deploy to Pub') {
                def MAVEN_OPTS = 'MAVEN_OPTS=-Dspring.profiles.active=qa'
                withEnv([MAVEN_OPTS]) {
                    sh 'mvn spring-boot:run'
                }
            }
            echo 'Run Success!'
        }catch (e){
            echo 'error!'
        }
    }
}