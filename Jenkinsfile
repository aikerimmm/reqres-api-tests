pipeline {
    agent any

    environment {
        REQRES_API_KEY = credentials('REQRES_API_KEY')
        TELEGRAM_BOT_TOKEN = credentials('TELEGRAM_BOT_TOKEN')
        TELEGRAM_CHAT_ID = credentials('TELEGRAM_CHAT_ID')
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
        always {
            allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'build/allure-results']]
            ])
        }

        success {
            echo 'API tests passed successfully'

            sh '''
                curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
                -d chat_id="${TELEGRAM_CHAT_ID}" \
                -d text="Reqres API Tests PASSED
Build: #${BUILD_NUMBER}
${BUILD_URL}"
            '''
        }

        failure {
            echo 'API tests failed'

            sh '''
                curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
                -d chat_id="${TELEGRAM_CHAT_ID}" \
                -d text="Reqres API Tests FAILED
Build: #${BUILD_NUMBER}
${BUILD_URL}"
            '''
        }
    }
}