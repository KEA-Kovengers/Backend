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
                    def lastBuildCommit = 'HEAD^'
                    if (fileExists('.last_build_commit')) {
                        lastBuildCommit = readFile('.last_build_commit').trim()
                        echo "Last Build Commit: ${lastBuildCommit}"
                    } else {
                        echo "No last build commit file found. Assuming first build."
                    }
                    def changes = sh(script: 'git diff --name-only ${lastBuildCommit} HEAD', returnStdout: true).trim()
                    env.ARTICLE_SERVICE_CHANGED = changes.contains('article-service') ? 'true' : 'false'
                    env.USER_SERVICE_CHANGED = changes.contains('user-service') ? 'true' : 'false'
                }
            }
        }
        stage('Build Spring Boot Project') {
            steps {
                script {
                    if (env.ARTICLE_SERVICE_CHANGED == 'true') {
                        dir('article-service') {
                            sh './gradlew build'
                        }
                    }
                    if (env.USER_SERVICE_CHANGED == 'true') {
                        dir('user-service') {
                            sh './gradlew build'
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
                            docker.build("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}")
                        }
                    }
                    if (env.USER_SERVICE_CHANGED == 'true') {
                        dir('user-service') {
                            docker.build("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}")
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
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}").push("${VERSION}")
                        }
                        if (env.USER_SERVICE_CHANGED == 'true') {
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}").push("${VERSION}")
                        }
                    }
                }
            }
        }

        stage('Save Last Commit Hash') {
            steps {
                script {
                    // 현재 커밋 해시를 파일에 저장
                    sh "git rev-parse HEAD > .last_build_commit"
                }
            }
        }
    }
}