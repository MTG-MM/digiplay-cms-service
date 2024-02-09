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
                    sh "sudo docker build -t ${RESPOSITORY}/${NAME}:${BUILD_NUMBER} ."
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

                  try{
                    sh "sudo docker rm ${NAME}"
                  } catch(Exception e) {
                     echo "No runnistop container found with the name ${NAME}."
                  }

                  if(BRANCH_NAME == 'master'){
                    try{
                        sh "sudo docker run --name ${NAME}-${BUILD_NUMBER} -d -p ${PORT}:8080 ${RESPOSITORY}/${NAME}:${BUILD_NUMBER}"
                    } catch(Exception e) {
                        def lastSuccessfulBuildID = 0
                        def build = currentBuild.previousBuild
                        while (build != null) {
                        f (build.result == "SUCCESS"){
                            lastSuccessfulBuildID = build.id as Integer
                            break
                            }
                            build = build.previousBuild
                         }
                       sh "sudo docker run --name ${NAME}-${build} -d -p ${PORT}:80 ${RESPOSITORY}/${NAME}:${BUILD_NUMBER}"

                    }
                  }


                  sh "sudo docker rename ${NAME}-${BUILD_NUMBER} ${NAME}"
                  try{
                    sh 'sudo docker container prune -f'
                    sh "sudo docker rmi \$(sudo docker images -q --filter \"before=${RESPOSITORY}/${NAME}:${BUILD_NUMBER-10}\")"
                  } catch(Exception e) {
                     echo "remove trash image, container"
                  }
               }
            }
        }

    }
}
