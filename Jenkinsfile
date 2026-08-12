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

        stage('Prepare Test Statistics') {
            steps {
                sh '''
                    TOTAL=$(find build/test-results/test -name "TEST-*.xml" \
                        -exec grep -h '<testsuite ' {} \\; \
                        | sed -E 's/.*tests="([0-9]+)".*/\\1/' \
                        | awk '{sum += $1} END {print sum+0}')

                    FAILED=$(find build/test-results/test -name "TEST-*.xml" \
                        -exec grep -h '<testsuite ' {} \\; \
                        | sed -E 's/.*failures="([0-9]+)".*/\\1/' \
                        | awk '{sum += $1} END {print sum+0}')

                    ERRORS=$(find build/test-results/test -name "TEST-*.xml" \
                        -exec grep -h '<testsuite ' {} \\; \
                        | sed -E 's/.*errors="([0-9]+)".*/\\1/' \
                        | awk '{sum += $1} END {print sum+0}')

                    SKIPPED=$(find build/test-results/test -name "TEST-*.xml" \
                        -exec grep -h '<testsuite ' {} \\; \
                        | sed -E 's/.*skipped="([0-9]+)".*/\\1/' \
                        | awk '{sum += $1} END {print sum+0}')

                    FAILED_TOTAL=$((FAILED + ERRORS))
                    PASSED=$((TOTAL - FAILED_TOTAL - SKIPPED))

                    if [ "$TOTAL" -gt 0 ]; then
                        PASSED_PERCENT=$((PASSED * 100 / TOTAL))
                    else
                        PASSED_PERCENT=0
                    fi

                    echo "$TOTAL" > total.txt
                    echo "$PASSED" > passed.txt
                    echo "$FAILED_TOTAL" > failed.txt
                    echo "$SKIPPED" > skipped.txt
                    echo "$PASSED_PERCENT" > passed_percent.txt

                    echo "Total: $TOTAL"
                    echo "Passed: $PASSED"
                    echo "Failed: $FAILED_TOTAL"
                    echo "Skipped: $SKIPPED"
                    echo "Passed percent: $PASSED_PERCENT%"
                '''
            }
        }

        stage('Generate Telegram Chart') {
            steps {
                sh '''
                    TOTAL=$(cat total.txt)
                    PASSED=$(cat passed.txt)
                    FAILED=$(cat failed.txt)

                    curl -G "https://quickchart.io/chart" \
                        --data-urlencode "width=700" \
                        --data-urlencode "height=420" \
                        --data-urlencode "format=png" \
                        --data-urlencode "backgroundColor=white" \
                        --data-urlencode "c={
                            type:'doughnut',
                            data:{
                                labels:['Passed','Failed'],
                                datasets:[{
                                    data:[$PASSED,$FAILED],
                                    backgroundColor:['#8BC34A','#F44336']
                                }]
                            },
                            options:{
                                cutoutPercentage:70,
                                title:{
                                    display:true,
                                    text:'Reqres API Tests',
                                    fontSize:24
                                },
                                legend:{
                                    position:'right',
                                    labels:{
                                        fontSize:16
                                    }
                                }
                            }
                        }" \
                        -o telegram-report.png

                    test -s telegram-report.png
                '''
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
                TOTAL=$(cat total.txt)
                PASSED=$(cat passed.txt)
                FAILED=$(cat failed.txt)
                SKIPPED=$(cat skipped.txt)
                PASSED_PERCENT=$(cat passed_percent.txt)

                curl -s -X POST \
                    "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendPhoto" \
                    -F chat_id="${TELEGRAM_CHAT_ID}" \
                    -F photo="@telegram-report.png" \
                    -F caption="Reqres API Tests

Results:
Environment: main
Comment: Regression run
Build: #${BUILD_NUMBER}

Total scenarios: ${TOTAL}
Total passed: ${PASSED} (${PASSED_PERCENT}%)
Total failed: ${FAILED}
Total skipped: ${SKIPPED}

Report available at:
${BUILD_URL}allure/"
            '''
        }

        failure {
            echo 'API tests failed'

            sh '''
                curl -s -X POST \
                    "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
                    -d chat_id="${TELEGRAM_CHAT_ID}" \
                    --data-urlencode "text=Reqres API Tests FAILED

Build: #${BUILD_NUMBER}

Check Jenkins console:
${BUILD_URL}

Allure Report:
${BUILD_URL}allure/"
            '''
        }
    }
}