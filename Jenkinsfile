 podTemplate(yaml: """
apiVersion: v1
kind: Pod
metadata:
  name: git-maven-docker-cicd
  namespace: cicd
  labels:
    name: m2-cache
spec:
  restartPolicy: Never
  nodeSelector:
    base_tools_internet: "true"
  containers:
  - name: git2262-mvn363
    image: harbor.cs.cptmcp.com:30723/library/ringcentral/maven:3.6.3-jdk8u202
    command: ["sleep", "1200"]
    volumeMounts:
      - mountPath: /root/.m2
        name: m2
  - name: docker-190312
    image: harbor.cs.cptmcp.com:30723/library/docker:19.03.12
    command: ["sleep", "1200"]
    volumeMounts:
      - mountPath: /var/run/docker.sock
        name: docker
  volumes:
    - name: m2
      persistentVolumeClaim:
        claimName: m2-pvc
    - name: docker
      hostPath:
          path: /var/run/docker.sock
"""
) {
    node(POD_LABEL) {
      stage('Clone') {
          container('git2262-mvn363') {
            echo "1.Clone Stage"
            git(
                url: "http://61.187.64.220:7907/zhangting/springboot-maven-pipline-demo.git",
                credentialsId: "c46b02d5-4a2a-4efc-9957-89129823160f",
                branch: "master"
            )
            script {
                build_tag = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
            }
            sh 'mvn clean package -DSkipTests'
        }
      }
      stage('Build') {
          container('docker-190312') {
            echo '${build_tag}'
            echo "3.Build Docker Image Stage"
            sh "docker build -f Dockerfile -t zhangting/springboot-maven-pipline-demo:0.0.1 ."
            sh "docker tag zhangting/springboot-maven-pipline-demo:0.0.1 harbor.cs.cptmcp.com:30723/temp/zhangting/springboot-maven-pipline-demo:0.0.1"
          }
      }
      stage('Push') {
        container('docker-190312') {
          echo "4.Push Docker Image Stage"
          sh "docker login harbor.cs.cptmcp.com:30723 -u admin -p Harbor12345"
          sh "docker push harbor.cs.cptmcp.com:30723/temp/zhangting/springboot-maven-pipline-demo:0.0.1"
        }
      }
    }
}
