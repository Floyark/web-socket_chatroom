node {
    docker.image('maven:3.5.4-jdk-11').inside {
        try {
            stage ('Build') {
                mvn 'clean package'
            }

            stage ('Deploy to Pub') {
                def MAVEN_OPTS = 'MAVEN_OPTS=-Dspring.profiles.active=qa'
                withEnv([MAVEN_OPTS]) {
                    mvn 'spring-boot:run'
                }
            }
            echo 'Run Success!'
        }catch (e){
            echo 'error!'
        }
    }
}