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
                    # Matar la app anterior (ahora sí con el patrón correcto)
                    pkill -f 'deploy/app.jar' || true
                    pkill -f 'server.port=8081' || true
                    sleep 1

                    mkdir -p deploy
                    cp web/target/mascotas-web-*.jar deploy/app.jar

                    # JENKINS_NODE_COOKIE evita que Jenkins mate el proceso al terminar el build
                    JENKINS_NODE_COOKIE=dontKillMe nohup java -jar deploy/app.jar --server.port=8081 > deploy/app.log 2>&1 &
                '''
            }
        }

        stage('Smoke test') {
            steps {
                sh '''
                    # host.docker.internal apunta a la Mac host desde dentro del contenedor
                    SMOKE_URL="http://host.docker.internal:8081/actuator/health"

                    for i in $(seq 1 30); do
                        if curl -fsS "$SMOKE_URL"; then
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