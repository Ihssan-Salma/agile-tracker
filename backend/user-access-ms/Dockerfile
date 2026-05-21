# --- STAGE 1: The Builder (The "Kitchen") ---
# We use a full Maven image with JDK 21 to compile the code
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only the pom first to cache dependencies (optimization)
COPY pom.xml .
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# --- STAGE 2: The Runtime (The "Dining Room") ---
# We use a much smaller JRE image just to run the JAR
FROM eclipse-temurin:21-jre
WORKDIR /app

# The logic: Copy from the "builder" stage, NOT from "build"
COPY --from=builder /app/target/*.jar app.jar

# User-Access-MS port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]