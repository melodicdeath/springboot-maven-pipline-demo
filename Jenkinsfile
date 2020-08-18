pipeline {
  agent {
      kubernetes {
        yamlFile 'podtemplate.yaml'
      }
    }
    stages {
      stage('Clone') {
          steps{
            container('git2262-mvn363') {
                echo "1.Clone Stage"
                git(
                    url: "http://61.187.64.220:7907/zhangting/springboot-maven-pipline-demo.git",
                    credentialsId: "gitlab_zhangting",
                    branch: "master"
                )
                script {
                    build_tag = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                }
                sh 'mvn clean package -DSkipTests'
            }
        }
      }
      stage('Build') {
        steps{
            container('docker-190312') {
                echo '${build_tag}'
                echo "3.Build Docker Image Stage"
                sh "docker build -f Dockerfile -t zhangting/springboot-maven-pipline-demo:0.0.1 ."
                sh "docker tag zhangting/springboot-maven-pipline-demo:0.0.1 harbor.cs.cptmcp.com:30723/temp/zhangting/springboot-maven-pipline-demo:0.0.1"
            }
          }
      }
      stage('Push') {
        steps{
            container('docker-190312') {
            echo "4.Push Docker Image Stage"
            withCredentials([usernamePassword(credentialsId: 'harbor_id', passwordVariable: 'pwd', usernameVariable: 'user')]) {
                sh "docker login harbor.cs.cptmcp.com:30723 -u ${user} -p ${pwd}"
            }
            sh "docker push harbor.cs.cptmcp.com:30723/temp/zhangting/springboot-maven-pipline-demo:0.0.1"
            }
        }
      }
    }
}
