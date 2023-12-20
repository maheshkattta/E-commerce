pipeline{
    agent{
        agent any
    }
    tools {
        gradle 'gradle'
    }
    stages{
        stage('gradle build'){
            steps{
               sh 'gradle wrapper && ./gradlew clean build'
            }
        stage('Copying Jar to target server'){
            steps{
                sh 'scp build/libs/ecommerence-0.0.1-SNAPSHOT.jar  mahi@manulabs.cloud:/home/mahi/ecommerence-0.0.1-SNAPSHOT-$BUILD_NUMBER.jar'
            }
        }
        stage('start jar'){
            steps{
                sh 'ssh mahi@manulabs.cloud /home/mahi/deploy.sh'
            }
        }
            post{
                success{
                    echo "========Pipeline executed successfully========"
                }
                failure{
                    echo "========Pipeline execution failed========"
                }
            }
        }
    }
}