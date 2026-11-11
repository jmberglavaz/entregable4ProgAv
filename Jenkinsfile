pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk   'JDK17'
    }

    triggers {
        githubPush()
        pollSCM('H/5 * * * *')
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests package'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'mvn -B test'
            }
        }

        stage('Integration tests') {
            steps {
                sh 'mvn -B verify'
            }
        }

        stage('Deploy simulado') {
            steps {
                sh '''
                    pkill -f 'mascotas-web-.*\\.jar' || true
                    mkdir -p deploy
                    cp web/target/mascotas-web-*.jar deploy/app.jar
                    nohup java -jar deploy/app.jar --server.port=8081 > deploy/app.log 2>&1 &
                '''
            }
        }

        stage('Smoke test') {
            steps {
                sh '''
                    for i in $(seq 1 30); do
                        if curl -fsS http://localhost:8081/actuator/health; then
                            exit 0
                        fi
                        sleep 1
                    done
                    echo '--- app.log ---'
                    cat deploy/app.log || true
                    exit 1
                '''
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml'
            archiveArtifacts artifacts: 'web/target/mascotas-web-*.jar', fingerprint: true, allowEmptyArchive: true
        }
    }
}