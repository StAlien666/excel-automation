# Этап 1 сборка
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# pom.xml
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Исходники копируем
COPY src ./src
RUN mvn -B package -DskipTests

# Этап 2 рантайм
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# так безопаснее
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build --chown=spring:spring /app/target/excel-automation-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]