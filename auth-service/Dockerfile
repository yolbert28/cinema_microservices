# =====================================================
# Stage 1: Build
# =====================================================
FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /app

# Cache de dependencias: copiar solo pom.xml primero
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw && ./mvnw dependency:go-offline -q

# Copiar fuentes y compilar
COPY src/ src/
RUN ./mvnw package -DskipTests -q

# =====================================================
# Stage 2: Runtime
# =====================================================
FROM eclipse-temurin:25-jre-alpine AS runtime

# Crear usuario no-root para seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Cambiar propietario
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
