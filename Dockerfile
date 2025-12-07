FROM gradle:jdk25 AS builder
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src src
RUN gradle clean build -x test

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8020
ENTRYPOINT ["java", "-jar", "app.jar"]