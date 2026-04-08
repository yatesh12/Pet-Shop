FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml pom.xml
COPY backend/petshop-shared/pom.xml backend/petshop-shared/pom.xml
COPY backend/petshop-catalog-service/pom.xml backend/petshop-catalog-service/pom.xml
COPY backend/petshop-shared/src backend/petshop-shared/src
COPY backend/petshop-catalog-service/src backend/petshop-catalog-service/src
RUN mvn -pl backend/petshop-catalog-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/backend/petshop-catalog-service/target/petshop-catalog-service-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
