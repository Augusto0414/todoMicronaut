# Etapa de construcción (builder)
FROM gradle:jdk17-alpine AS builder

WORKDIR /app
COPY . /app

# Construir el JAR excluyendo las pruebas
RUN gradle build -x test

# Etapa de ejecución (runtime)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el archivo JAR construido desde la etapa de construcción
COPY --from=builder /app/build/libs/*-all.jar app.jar

# Exponer el puerto por defecto de Micronaut
EXPOSE 8080

# Variables de entorno para la JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
