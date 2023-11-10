FROM openjdk:17-jdk-slim
LABEL authors="alexwu"
COPY mylo-api-gateway/target/mylo-api-gateway-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]