FROM maven:3.9-eclipse-temurin-25 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 9191
EXPOSE 9190
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9190", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-jar", "app.jar"]