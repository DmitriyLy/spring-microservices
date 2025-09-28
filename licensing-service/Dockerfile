FROM openjdk:25-jdk-slim
WORKDIR app
COPY ./target/*.jar app.jar
EXPOSE 9191
ENTRYPOINT ["java", "-jar", "app.jar"]