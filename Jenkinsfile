pipeline {
    agent any

    environment {
        REQRES_API_KEY = credentials('REQRES_API_KEY')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run API Tests') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean test'
            }
        }
    }

    post {
        success {
            echo 'API tests passed successfully'
        }

        failure {
            echo 'API tests failed'
        }
    }
}