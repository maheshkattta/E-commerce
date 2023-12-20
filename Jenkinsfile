pipeline {
    agent any

    tools {
        gradle 'gradle'
    }
    stages{
        stage('vars-checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github_token', url: 'https://github.com/maheshkattta/env-vars.git']]])
            }
        }
    }
    stage('inject-vars') {
        steps {
            sh 'cp application.properties src/main/resources/applicaton.properties'
        }
    }
    stage('gradle build') {
        steps {
            sh 'gradle wrapper && ./gradlew clean build'
        }
    }
    stage('Copying Jar to target server') {
        steps {
            sh 'scp build/libs/monkeystore-0.0.1-SNAPSHOT.jar  mahi@manulabs.cloud:/home/mahi/monkeystore-0.0.1-SNAPSHOT-$BUILD_NUMBER.jar'
        }
    }
    stage('start jar') {
        steps {
            sh 'ssh mahi@manulabs.cloud /home/mahi/deploy.sh'
        }
    }
}
