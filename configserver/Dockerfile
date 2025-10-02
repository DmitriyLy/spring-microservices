FROM openjdk:25-jdk-slim
WORKDIR app
COPY ./target/*.jar app.jar
EXPOSE 9292
EXPOSE 9291
RUN apt update && apt install curl -y
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9290", "-jar", "app.jar"]