FROM harbor.cs.cptmcp.com:30723/library/ringcentral/jdk:8u202
WORKDIR /opt/cicd
EXPOSE 8080
# ENTRYPOINT ["java"]
COPY ./target/springboot-maven-pipline-demo-0.0.1-SNAPSHOT.jar /opt/cicd
RUN ["chmod", "+x", "springboot-maven-pipline-demo-0.0.1-SNAPSHOT.jar"]
CMD ["java","-jar","springboot-maven-pipline-demo-0.0.1-SNAPSHOT.jar"]
