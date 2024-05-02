pipeline {
    agent any
    environment {
        DOCKER_CREDENTIAL_ID = 'docker_credentials'
        DOCKER_HUB_USERNAME = 'kovengers'
        IMAGE_NAME_ARTICLE_SERVICE = 'article-service'
        IMAGE_NAME_USER_SERVICE = 'user-service'
        VERSION = "${env.BUILD_NUMBER}" // Jenkins 빌드 번호를 버전으로 사용합니다.
    }
    stages {
        stage('Check Git Changes') {
            steps {
                script {
                // 빌드 원인 확인
                    def causes = currentBuild.getBuildCauses()
                    
                    if (causes.any { it._class == 'hudson.model.Cause$UserIdCause' }) {
                            echo "This build was started manually."
                            env.ARTICLE_SERVICE_CHANGED = 'true'
                            env.USER_SERVICE_CHANGED = 'true'
                    } else {
                        echo "This build was not started manually."

                        def lastBuildCommit = 'HEAD^'
                        if (fileExists('.last_build_commit')) {
                            lastBuildCommit = readFile('.last_build_commit').trim()
                            echo "Last Build Commit: ${lastBuildCommit}"
                        } else {
                            echo 'No last build commit file found. Assuming first build.'
                        }
                        def changes = sh(script: "git diff --name-only ${lastBuildCommit} HEAD", returnStdout: true).trim()
                        echo "Changes: ${changes}"
                        env.ARTICLE_SERVICE_CHANGED = changes.contains('article-service') ? 'true' : 'false'
                        env.USER_SERVICE_CHANGED = changes.contains('user-service') ? 'true' : 'false'   
                    }
                    
                }
            }
        }
        stage('Copy YAML File') {
            steps {
                script{
                    if (env.ARTICLE_SERVICE_CHANGED == 'true') {
                    // YAML 파일을 credential로부터 읽어와서 특정 위치에 복사
                    withCredentials([file(credentialsId: 'article-application', variable: 'ARTICLE_YML_FILE')]) {
                        // 파일 복사 명령 실행
                        sh('sudo mkdir -p ' + WORKSPACE + '/config/article-service-module/')
                        sh('sudo cp ' + ARTICLE_YML_FILE + ' ' + WORKSPACE + '/config/article-service-module/application.yml')
                    }
                    }
                    if (env.USER_SERVICE_CHANGED == 'true') {
                        // YAML 파일을 credential로부터 읽어와서 특정 위치에 복사
                        withCredentials([file(credentialsId: 'user-application', variable: 'USER_YML_FILE')]) {
                            // 파일을 빌드 디렉토리 내 특정 위치로 복사
                            // 파일 복사 명령 실행
                            sh('sudo mkdir -p ' + WORKSPACE + '/config/user-service-module/')
                            sh('sudo cp ' + USER_YML_FILE + ' ' + WORKSPACE + '/config/user-service-module/application.yml')
                        }
                    }
                }
            }
        }
        stage('Build Docker images') {
            steps {
                script {
                    if (env.ARTICLE_SERVICE_CHANGED == 'true') {
                        dir('article-service') {
                            docker.build("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}", "-t ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:latest -t ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION} .")

                            echo "Build ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}"
                        }
                    }
                    if (env.USER_SERVICE_CHANGED == 'true') {
                        dir('user-service') {
                            docker.build("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}", "-t ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:latest -t ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION} .")

                            echo "Build ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION}"
                        }
                    }
                }
            }
        }
        stage('Push Docker images') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', "${DOCKER_CREDENTIAL_ID}") {
                        if (env.ARTICLE_SERVICE_CHANGED == 'true') {
                            // 'latest' 태그 푸시
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:latest").push()
                            // 버전 태그 푸시
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}").push()

                            echo "Push ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}"
                        }
                        if (env.USER_SERVICE_CHANGED == 'true') {
                            // 'latest' 태그 푸시
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:latest").push()
                            // 버전 태그 푸시
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION}").push()

                            echo "Push ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION}"
                        }
                    }
                }
            }
        }
        stage('Docker image cleanup') {
            steps {
                script {
                    if (env.ARTICLE_SERVICE_CHANGED == 'true') {
                        sh 'docker rmi ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}'
                        sh 'docker rmi registry.hub.docker.com/${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}'
                        echo "rmi ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}"
                    }
                    if (env.USER_SERVICE_CHANGED == 'true') {
                        sh 'docker rmi ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION}'  
                        sh 'docker rmi registry.hub.docker.com/${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION}'                  
                        echo "rmi ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION}"
                    }
                }
            }
        }
        
        stage('Save Last Commit Hash') {
            steps {
                script {
                    // 현재 커밋 해시를 파일에 저장
                    sh 'git rev-parse HEAD > .last_build_commit'
                }
            }
        }
    }
}
