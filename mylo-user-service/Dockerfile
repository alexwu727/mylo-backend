FROM openjdk:17-jdk-slim
LABEL authors="alexwu"
COPY mylo-user-service/target/mylo-user-service-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]