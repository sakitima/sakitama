FROM swr.cn-east-3.myhuaweicloud.com/ssbase/maven-jdk17:v1.0.1-test as mvn
ADD . /opt
RUN cd /opt/ && mvn clean package -Dmaven.test.skip=true

FROM swr.cn-east-3.myhuaweicloud.com/ssbase/openjdk11:v1.0.2-test1 as run
WORKDIR /opt
COPY --from=mvn /opt/@{APP_NAME}/target/@{APP_NAME}.jar /opt/app.jar
EXPOSE 8080
