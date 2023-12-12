FROM openjdk:17-jdk-slim
LABEL authors="alexwu"
VOLUME /tmp
EXPOSE 8080
COPY target/mylo-user-service-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
# docker build -t mylo-user-service -v /var/lib/docker/volumes/mylo-user-service/_data:/tmp .