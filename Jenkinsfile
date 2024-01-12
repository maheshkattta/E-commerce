pipeline {
  agent any
  stages {
    stage('Injecting Variales') {
      steps {
        dir(path: "/var/lib/jenkins/workspace/$JOB_NAME-properties") {
          git(branch: 'main', credentialsId: 'github_token', url: 'https://github.com/maheshkattta/env-vars.git', poll: true)
          sh 'chmod +x ./copy-creds.sh'
          sh './copy-creds.sh'
        }

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
        sh 'chmod +x deploy.sh'
        sh 'scp deploy.sh mahi@manulabs.cloud:/home/mahi/deploy.sh'
      }
    }

    stage('start jar') {
      steps {
        sh 'ssh mahi@manulabs.cloud /home/mahi/deploy.sh'
      }
    }

  }
  tools {
    gradle 'gradle'
  }
}