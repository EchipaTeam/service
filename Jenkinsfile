pipeline {
    agent any

     environment {
            DOCKER_PASSWORD = credentials("123456789")
            GITHUB_TOKEN = credentials("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")
    }

    stages {
        stage('Gradle') {
            steps {
                sh 'gradle clean build'
            }
        }
        stage('Unit and Integration Testing') {
            steps {
                sh './gradlew test --info'
            }
        }
        stage('Tag image') {
            steps {
                script {
                   sh([script: 'git fetch --tag', returnStdout: true]).trim()
                   env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                   env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                   env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                   env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                }
                sh "docker build -t lucianblaga/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
            }
        }


        stage('Run Application') {
            steps {
                sh "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
            }
        }
    }
}