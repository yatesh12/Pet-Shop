FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml pom.xml
COPY backend/petshop-shared/pom.xml backend/petshop-shared/pom.xml
COPY backend/petshop-commerce-service/pom.xml backend/petshop-commerce-service/pom.xml
COPY backend/petshop-shared/src backend/petshop-shared/src
COPY backend/petshop-commerce-service/src backend/petshop-commerce-service/src
RUN mvn -pl backend/petshop-commerce-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/backend/petshop-commerce-service/target/petshop-commerce-service-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
