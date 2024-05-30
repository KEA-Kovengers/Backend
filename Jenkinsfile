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
        stage('Create K8S ConfigMap') {
            steps {
                script {
                    if (env.CONFIG_CHANGED == 'true') {
                        dir('config') {
                            sh 'sudo kubectl create configmap article-service-config --from-file=article-service-module/application.yml --dry-run=client -o yaml > article-service-configmap.yml'
                            sh 'sudo kubectl create configmap user-service-config --from-file=user-service-module/application.yml --dry-run=client -o yaml > user-service-configmap.yml'
                            // sh 'sudo kubectl create configmap notice-service-config --from-file=notice-service-module/application.yml --dry-run=client -o yaml > notice-service-configmap.yml'
                        }
                    }
                }
            }
        }
        stage('Push K8S ConfigMap') {
            steps {
                script {
                    if (env.CONFIG_CHANGED == 'true') {
                        dir('config') {              
                            sshagent(['k8s_git']) {
                                sh 'mkdir -p ~/.ssh'
                                sh 'if [ ! -f ~/.ssh/known_hosts ]; then ssh-keyscan github.com >> ~/.ssh/known_hosts; fi'
                                sh 'rm -rf kubernetes-yaml' // Add this line
                                sh 'git clone git@github.com:KEA-Kovengers/kubernetes-yaml.git'
                            }
                            dir('kubernetes-yaml/backend') {
                                sh 'git config user.email "keakovengers@gmail.com"'
                                sh 'git config user.name "kovengers"'
                                dir('article-service'){
                                    sh 'cp ../../../article-service-configmap.yml .'
                                    sh 'git add article-service-configmap.yml'
                                }
                                dir('user-service'){
                                    sh 'cp ../../../user-service-configmap.yml .'
                                    sh 'git add user-service-configmap.yml'
                                }
                                // dir('notice-service'){
                                //     sh 'cp ../../../notice-service-configmap.yml .'
                                //     sh 'git add user-service-configmap.yml'
                                // }
                                
                                sh 'git diff --exit-code || git commit -m "Update ConfigMap"'
                                sshagent(['k8s_git']) {
                                    sh 'git push origin kakao-cloud'
                                }
                            }
                        }
                    }
                }
            }
        }
        stage('Update Kubernetes YAML') {
            steps {
                script {
                    dir('config'){
                        sshagent(['k8s_git']) {
                            sh 'mkdir -p ~/.ssh'
                            sh 'if [ ! -f ~/.ssh/known_hosts ]; then ssh-keyscan github.com >> ~/.ssh/known_hosts; fi'
                            sh 'rm -rf kubernetes-yaml' // Add this line
                            sh 'git clone git@github.com:KEA-Kovengers/kubernetes-yaml.git'
                            sh 'git checkout kakao-cloud'
                            sh 'git config user.email "keakovengers@gmail.com"'
                            sh 'git config user.name "kovengers"'
                        }
                        dif('kubernetes-yaml')
                        if (env.ARTICLE_SERVICE_CHANGED == 'true') {
                            dir('backend/article-service'){
                                sh "sed -i 's|${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:.*|${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}:${VERSION}|' article-service.yaml"
                                sh 'git add article-service.yaml'
                                sh 'git diff --exit-code || git commit -m "Update article service image tag"'
                            }
                        }
                        if (env.USER_SERVICE_CHANGED == 'true') {
                            dir('backend/user-service'){
                                sh "sed -i 's|${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:.*|${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}:${VERSION}|' user-service.yaml"
                                sh 'git add user-service.yaml'
                                sh 'git diff --exit-code || git commit -m "Update user service image tag"'
                            }
                        }
                        // if (env.NOTICE_SERVICE_CHANGED == 'true') {
                        //     dir('backend/notice-service'){
                        //         sh "sed -i 's|${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:.*|${DOCKER_HUB_USERNAME}/${IMAGE_NAME_NOTICE_SERVICE}:${VERSION}|' notice-service.yaml"
                        //         sh 'git add notice-service.yaml'
                        //         sh 'git diff --exit-code || git commit -m "Update notice service image tag"'
                        //     }
                        // }
                        sshagent(['k8s_git']) {
                            sh 'git push kakao-cloud'
                        }
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
