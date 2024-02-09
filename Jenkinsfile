pipeline {
    agent any
    environment {
        BRANCH_NAME = "${GIT_BRANCH.split("/")[1]}"
        DOCKER_HUB_URL="lambro2510"
        RESPOSITORY="lambro2510"
        DOCKER_HUB_TOKEN=credentials("docker_hub_token")
        NAME="mos-cms-service"
        PORT="8900"
    }
    stages {
        stage('Build') {
            steps {
                script {
                  sh "sudo docker login | echo ${RESPOSITORY} | echo ${DOCKER_HUB_TOKEN}"
                    sh "sudo docker build -t lambro2510/${NAME}:${BUILD_NUMBER} ."
                }
            }
        }

        stage('Deploy') {
            steps {
                script {

                  try{
                    sh "sudo docker stop ${NAME}"
                  } catch(Exception e) {
                     echo "No running container found with the name ${NAME}."
                  }

                  if(BRANCH_NAME == 'master'){
                    try{
                     sh "sudo docker run --name ${NAME} -d -p ${PORT}:80 ${RESPOSITORY}/${NAME}:${BUILD_NUMBER}"
                    }catch(Exception e) {
                     def previousSuccessfulBuildNumber = findPreviousSuccessfulBuild(env.JOB_NAME, env.BUILD_NUMBER)
                     def previousImageVersion = "${DOCKER_HUB_URL}/${NAME}:${previousSuccessfulBuildNumber}"
                     sh "sudo docker run --name ${NAME} -d -p ${PORT}:80 ${previousImageVersion}"
                    }
                  }

                  try{
                    sh 'sudo docker container prune -f'
                    sh 'sudo docker image prune -f'
                  } catch(Exception e) {
                     echo "remove trash image"
                  }
               }
            }
        }

    }
}

def findPreviousSuccessfulBuild(jobName, currentBuildNumber) {
    def buildNumber = currentBuildNumber.toInteger() - 1

    while (buildNumber > 0) {
        def buildInfo = currentBuild.rawBuild.getProject().getBuildByNumber(buildNumber)

        if (buildInfo.getResult().toString() == 'SUCCESS') {
            return buildNumber
        }

        buildNumber--
    }

    return 0
}