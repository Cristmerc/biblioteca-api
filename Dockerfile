# Paso 1: Compilar la aplicación
FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Ejecutar la aplicación
FROM eclipse-temurin:17-alpine

# El puente definitivo para que Docker lea las variables en tiempo de ejecución
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
