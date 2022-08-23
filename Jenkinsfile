pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '3333', url: 'https://github.com/LeechenLove/ApiAutomation.git']]])
            }
        }
        stage('mvn') {
            steps {
                bat 'mvn clean test -Dtestset=smoketest.xml'
            }

            post {
               always {
                 step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
               }
             }
        }
    }
}