FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN mvnw clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build ./target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

