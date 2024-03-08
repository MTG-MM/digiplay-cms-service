pipeline {
    agent any
    environment {
        BRANCH_NAME = "${GIT_BRANCH.split("/")[1]}"
        DOCKER_HUB_URL="lambro2510"
        RESPOSITORY="lambro2510"
        DOCKER_HUB_TOKEN=credentials("docker_hub_token")
        NAME="mos-cms-service"
        PORT="31000"
        DATASOURCE_URL="jdbc:mysql://103.69.194.195:25035/prod"
        DATASOURCE_USERNAME="root"
        DATASOURCE_PASSWORD="banhmy09"
        REDIS_ADDRESS="redis://:banhmy09@103.69.194.5:13437"
        JWT_SECRET="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"
        JWT_EXPIRE_TIME=7200000
        JWT_REFRESH_EXPIRE_TIME=1296000
    }
    stages {
        stage('Build') {
            steps {
                script {
                  sh "sudo docker build -t ${RESPOSITORY}/${NAME}:${BUILD_NUMBER} ."
                }
            }
        }

//         stage('Deploy') {
//             steps {
//                 script {
//
//                   try{
//                     sh "sudo docker stop ${NAME}"
//                   } catch(Exception e) {
//                      echo "No running container found with the name ${NAME}."
//                   }
//
//                   try{
//                     sh "sudo docker rm ${NAME}"
//                   } catch(Exception e) {
//                      echo "No running container found with the name ${NAME}."
//                   }
//
//             if (BRANCH_NAME == 'master') {
//                 try {
//                     sh """
//                         sudo docker run \
//                         --name ${NAME}-${BUILD_NUMBER} \
//                         -d -p ${PORT}:8080 \
//                         --cap-add=SYS_PTRACE --privileged \
//                         -v \"/var/run/docker.sock:/var/run/docker.sock\" \
//                         -e DATASOURCE_URL=${DATASOURCE_URL} \
//                         -e DATASOURCE_USERNAME=${DATASOURCE_USERNAME} \
//                         -e DATASOURCE_PASSWORD=${DATASOURCE_PASSWORD} \
//                         -e REDIS_ADDRESS=${REDIS_ADDRESS} \
//                         -e JWT_SECRET=${JWT_SECRET} \
//                         -e JWT_EXPIRE_TIME=${JWT_EXPIRE_TIME} \
//                         -e JWT_REFRESH_EXPIRE_TIME=${JWT_REFRESH_EXPIRE_TIME} \
//                         ${RESPOSITORY}/${NAME}:${BUILD_NUMBER}
//                     """
//                 } catch (Exception e) {
//                     // Handle the exception
//                     // You can also use 'echo' to print error messages
//                 }
//             }
//
//
//                   sh "sudo docker rename ${NAME}-${BUILD_NUMBER} ${NAME}"
//                   try{
//                     sh "sudo docker rmi \$(sudo docker images -q --filter \"before=${RESPOSITORY}/${NAME}:${BUILD_NUMBER-10}\")"
//                   } catch(Exception e) {
//                      echo "remove trash image, container"
//                   }
//                }
//             }
//         }
        stage('Push') {
            steps {
                script {
                  sh "sudo docker login --username=${RESPOSITORY} --password=${DOCKER_HUB_TOKEN} docker.io"
                  sh "sudo docker tag ${RESPOSITORY}/${NAME}:${BUILD_NUMBER} ${RESPOSITORY}/${NAME}:latest"
                  sh "sudo docker push ${RESPOSITORY}/${NAME}:${BUILD_NUMBER}"
                  sh "sudo docker push ${RESPOSITORY}/${NAME}:latest"
                  sh "docker rmi -f  $(docker images -a -q)"
                }
            }
        }
    }
}
