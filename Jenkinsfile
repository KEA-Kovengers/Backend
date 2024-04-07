pipeline {
    agent any
    environment {
        DOCKER_CREDENTIAL_ID = 'docker_credentials'
        DOCKER_HUB_USERNAME = 'kovengers'
        IMAGE_NAME_ARTICLE_SERVICE = 'article-service'
        IMAGE_NAME_USER_SERVICE = 'user-service'
    }
    stages {
        stage('Build Spring Boot Project') {
            steps {
                dir('article-service') {
                    sh './gradlew build'
                }
                dir('user-service') {
                    sh './gradlew build'
                }
            }
        }
        stage('Build Docker images') {
            steps {
                dir('article-service') {
                    script {
                        docker.build("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}")
                    }
                }
                dir('user-service') {
                    script {
                        docker.build("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}")
                    }
                }
            }
        }
        stage('Push Docker images') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', "${DOCKER_CREDENTIAL_ID}") {
                        docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_ARTICLE_SERVICE}").push()
                        docker.image("${DOCKER_HUB_USERNAME}/${IMAGE_NAME_USER_SERVICE}").push()
                    }
                }
            }
        }
    }
}