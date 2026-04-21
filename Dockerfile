# Step 1: Build stage (Use Maven image)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copy the pom.xml and source code
COPY . .
# Run the build using the Maven installed in this image
RUN mvn clean package -DskipTests

# Step 2: Run stage (Use OpenJDK image)
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copy only the built .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]