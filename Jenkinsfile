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
        always {
            allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'build/allure-results']]
            ])
        }

        success {
            sh '''
            curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
            -d chat_id="${TELEGRAM_CHAT_ID}" \
            -d text="✅ Reqres API Tests PASSED
Build: #${BUILD_NUMBER}
${BUILD_URL}"
        '''
        }

        failure {
            sh '''
            curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
            -d chat_id="${TELEGRAM_CHAT_ID}" \
            -d text="❌ Reqres API Tests FAILED
Build: #${BUILD_NUMBER}
${BUILD_URL}"
        '''
        }
    }

        success {
            echo 'API tests passed successfully'
        }

        failure {
            echo 'API tests failed'
        }
    }
}

environment {
    REQRES_API_KEY = credentials('REQRES_API_KEY')
    TELEGRAM_BOT_TOKEN = credentials('TELEGRAM_BOT_TOKEN')
    TELEGRAM_CHAT_ID = credentials('TELEGRAM_CHAT_ID')
}