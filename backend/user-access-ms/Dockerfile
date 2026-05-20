# --- Build Stage ---
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# 1. Copy ONLY the pom.xml first to isolate dependencies
COPY pom.xml .

# 2. Go offline to download and cache all dependencies 
RUN mvn dependency:go-offline -B

# 3. Now copy the source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]