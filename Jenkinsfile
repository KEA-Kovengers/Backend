pipeline {
    agent any
    environment {
        DOCKER_CREDENTIAL_ID = 'docker_credentials'
        DOCKER_HUB_USERNAME = 'kovengers'
        IMAGE_NAME_ARTICLE_SERVICE = 'article-service'
        IMAGE_NAME_USER_SERVICE = 'user-service'
        IMAGE_NAME_NOTICE_SERVICE = 'notice-service'
        VERSION = "${env.BUILD_NUMBER}" // Jenkins 빌드 번호를 버전으로 사용합니다.
        K8S_REPO = 'github.com/KEA-Kovengers/kubernetes-yaml'
        PATH = "/home/ubuntu/:$PATH"
        KUBE_CONFIG = "/home/ubuntu/kubeconfig-kovengers.yaml"
        KUBECONFIG = "/home/ubuntu/kubeconfig-kovengers.yaml"

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
                            env.NOTICE_SERVICE_CHANGED = 'true'
                            env.CONFIG_CHANGED = 'true'
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
                        env.NOTICE_SERVICE_CHANGED = changes.contains('notice-service') ? 'true' : 'false'   
                        env.CONFIG_CHANGED = changes.contains('config') ? 'true' : 'false'
                    }
                    
                }
            }
        }
        stage('Pull Git Submodules') {
            steps {
                sh 'git submodule update --init --recursive'
            }
        }
        stage('Update K8S ConfigMap') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'KOV-githubapp', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        if (env.CONFIG_CHANGED == 'true') {
                            dir('config') {
                                sh 'git config user.email "kmjung1515@naver.com"'
                                sh 'git config user.name "wooing1084"'
                                sh 'sudo kubectl create configmap article-service-config --from-file=article-service-module/application.yml --dry-run=client -o yaml > article-service-configmap.yml'
                                sh 'sudo kubectl create configmap user-service-config --from-file=user-service-module/application.yml --dry-run=client -o yaml > user-service-configmap.yml'
                                // sh 'sudo kubectl create configmap notice-service-config --from-file=notice-service-module/application.yml --dry-run=client -o yaml > notice-service-configmap.yml'
                                sh 'git add article-service-configmap.yml'
                                sh 'git add user-service-configmap.yml'
                                // sh 'git add backend/configmap/notice-service-configmap.yml'
                                sh 'git commit -m "Update ConfigMap"'
                                sh "git push https://${USERNAME}:${PASSWORD}@${env.K8S_REPO} kakao-cloud"
                            }
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
                    if (env.NOTICE_SERVICE_CHANGED == 'true') {
                        dir('article-service') {
                            docker.build("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}", "-t ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:latest -t ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:${VERSION} .")

                            echo "Build ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}"
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
                        if (env.NOTICE_SERVICE_CHANGED == 'true') {
                            // 'latest' 태그 푸시
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:latest").push()
                            // 버전 태그 푸시
                            docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:${VERSION}").push()

                            echo "Push ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:${VERSION}"
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
                    if (env.NOTICE_SERVICE_CHANGED == 'true') {
                        sh 'docker rmi ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:${VERSION}'  
                        sh 'docker rmi registry.hub.docker.com/${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:${VERSION}'                  
                        echo "rmi ${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:${VERSION}"
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
