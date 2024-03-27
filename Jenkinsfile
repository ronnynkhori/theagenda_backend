pipeline {
    agent any
    tools{
        maven 'maven-3.6.3'
        jdk 'java'
    }

    stages {
        stage('CLEAN & PACKAGE ARTIFACT') {
            steps {
                sh "mvn clean package"
            }
            post{
                success {
                    slackSend color:'good', message:"CLEAN & PACKAGE ARTIFACT stage of ${env.JOB_NAME} on branch ${env.BRANCH_NAME} is successful.\nVisit ${env.RUN_DISPLAY_URL} to view the results"
                }

                failure {
                    slackSend color:'danger', message:"CLEAN & PACKAGE ARTIFACT stage of ${env.JOB_BASE_NAME} on branch ${env.BRANCH_NAME} has failed.\nVisit ${env.RUN_DISPLAY_URL} to view the results"
                }
            }
        }

        stage('DEPLOYMENT TO DIGITAL OCEAN') {
            steps {
                sshagent (credentials: ['DigitalOceanSSH']) {
                 sh "ssh -o StrictHostKeyChecking=no rnkhori@159.223.194.235 'mkdir -p /home/rnkhori/theagenda/backend'"
                  sh "scp -r -o StrictHostKeyChecking=no  target/theagenda-1.0.0.jar rnkhori@159.223.194.235:~/theagenda/backend/theagenda.jar"
                  sh "ssh rnkhori@159.223.194.235 'cd ~/theagenda && docker compose up -d --force-recreate --build backend'"
                }
            }

            post{
                success {
                    slackSend color:'good', message:"""DEPLOYMENT #${env.BUILD_NUMBER} to DIGITAL OCEAN of branch ${env.BRANCH_NAME}
                    completed successfully. Visit ${env.RUN_DISPLAY_URL} to view the results."""
                }
                failure {
                    slackSend color:'danger', message:"""DEPLOYMENT #${env.BUILD_NUMBER} to DIGITAL OCEAN of branch ${env.BRANCH_NAME}
                     has failed. Visit ${env.RUN_DISPLAY_URL} to view the results."""
                }
            }

        }

    }
}