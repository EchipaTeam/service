pipeline {
    agent any
     environment {
         DOCKER_PASSWORD = credentials("123456789")
         GITHUB_TOKEN = credentials("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")
     }

     stages {
         stage('Build & Test') {
             steps {
                 sh './gradlew clean build'
             }
         }
         stage('Tag image') {
               steps {
                 script {
                     GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
                     MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
                     MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
                     PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
                 }
                 sh "docker build -t zamfirescustefan/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
               }
         }
     }
 }
