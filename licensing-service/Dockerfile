FROM openjdk:25-jdk-slim
WORKDIR app
COPY ./target/*.jar app.jar
EXPOSE 9191
EXPOSE 9190
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9190", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-jar", "app.jar"]