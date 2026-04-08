FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml pom.xml
COPY backend/petshop-shared/pom.xml backend/petshop-shared/pom.xml
COPY backend/petshop-catalog-service/pom.xml backend/petshop-catalog-service/pom.xml
COPY frontend/petshop-web/pom.xml frontend/petshop-web/pom.xml
COPY backend/petshop-shared/src backend/petshop-shared/src
COPY backend/petshop-catalog-service/src backend/petshop-catalog-service/src
COPY frontend/petshop-web/src frontend/petshop-web/src
RUN mvn -pl frontend/petshop-web -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/frontend/petshop-web/target/petshop-web-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "if [ -n \"${PETSHOP_SERVICES_CATALOG_HOSTPORT:-}\" ] && [ -z \"${PETSHOP_SERVICES_CATALOG_BASE_URL:-}\" ]; then export PETSHOP_SERVICES_CATALOG_BASE_URL=\"http://${PETSHOP_SERVICES_CATALOG_HOSTPORT}\"; fi; if [ -n \"${PETSHOP_SERVICES_COMMERCE_HOSTPORT:-}\" ] && [ -z \"${PETSHOP_SERVICES_COMMERCE_BASE_URL:-}\" ]; then export PETSHOP_SERVICES_COMMERCE_BASE_URL=\"http://${PETSHOP_SERVICES_COMMERCE_HOSTPORT}\"; fi; exec java ${JAVA_OPTS:-} -jar /app/app.jar"]
