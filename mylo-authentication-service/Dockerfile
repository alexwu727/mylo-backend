FROM openjdk:17-jdk-slim
LABEL authors="alexwu"
COPY mylo-authentication-service/target/mylo-authentication-service-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]