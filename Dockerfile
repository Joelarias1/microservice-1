# Etapa de Construcción
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de Ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar la wallet desde el contexto de build local (ubicación correcta)
COPY src/main/resources/wallet ./wallet
ENV TNS_ADMIN=/app/wallet

# Copiar el JAR desde la etapa de construcción
COPY --from=builder /build/target/demo-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"] 