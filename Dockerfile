FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY ./ ./
RUN ./gradlew clean bootJar

FROM eclipse-temurin:17-jdk-alpine as local-run
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]