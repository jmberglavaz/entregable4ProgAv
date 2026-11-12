pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk   'JDK17'
    }

    triggers {
        // githubPush() no funciona sin webhook público (ngrok). Lo dejamos comentado o eliminado.
        pollSCM('H/5 * * * *') // Poll cada 5 min (suficiente para la demo)
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
                // verify corre failsafe (ITs) y surefire de nuevo, pero asegura el ciclo completo
                sh 'mvn -B verify'
            }
        }

        stage('Deploy simulado') {
            steps {
                sh '''
                    pkill -f 'deploy/app.jar' || true
                    sleep 1
                    mkdir -p deploy
                    cp web/target/mascotas-web-*.jar deploy/app.jar
                    
                    # setsid evade el ProcessTreeKiller de Jenkins
                    setsid java -jar deploy/app.jar --server.port=8081 --server.address=0.0.0.0 > deploy/app.log 2>&1 < /dev/null &
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
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml'
            archiveArtifacts artifacts: 'web/target/mascotas-web-*.jar', fingerprint: true, allowEmptyArchive: true
        }
    }
}