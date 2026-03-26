# Build stage
FROM container-registry.oracle.com/java/openjdk:21.0.2 AS build
WORKDIR /app
COPY src .
RUN mvn clean package -DskipTests

# Package stage
FROM container-registry.oracle.com/java/openjdk:21.0.2
WORKDIR /app
COPY --from=build /app/target/song-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


