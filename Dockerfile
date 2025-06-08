# === STAGE 1: Build ===
FROM eclipse-temurin:21-jdk-alpine AS builder

# Cria diretório de trabalho
WORKDIR /app

# Copia o wrapper e arquivos de build
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Instala dependências sem executar a build completa
RUN chmod +x gradlew && \
    ./gradlew dependencies --no-daemon

# Copia o código-fonte e gera o JAR
COPY src src
RUN ./gradlew bootJar --no-daemon

# === STAGE 2: Runtime ===
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o JAR gerado
COPY --from=builder /app/build/libs/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
