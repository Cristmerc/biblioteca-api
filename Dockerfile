# Paso 1: Compilar la aplicación pasando las variables de Render
FROM maven:3-eclipse-temurin-17 AS build

# Declaramos que recibiremos las variables desde Render
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD

# Las convertimos en variables de entorno para el proceso de compilación
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ENV SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD

COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Crear el entorno ligero de ejecución
FROM eclipse-temurin:17-alpine

# Volvemos a pasar las variables al contenedor final que correrá en internet
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ENV SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD

COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

