FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml pom.xml
COPY backend/petshop-shared/pom.xml backend/petshop-shared/pom.xml
COPY backend/petshop-gateway/pom.xml backend/petshop-gateway/pom.xml
COPY backend/petshop-shared/src backend/petshop-shared/src
COPY backend/petshop-gateway/src backend/petshop-gateway/src
RUN mvn -pl backend/petshop-gateway -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/backend/petshop-gateway/target/petshop-gateway-1.0.0.jar app.jar
EXPOSE 10000
ENTRYPOINT ["sh", "-c", "if [ -n \"${PETSHOP_ROUTES_WEB_HOSTPORT:-}\" ] && [ -z \"${PETSHOP_ROUTES_WEB:-}\" ]; then export PETSHOP_ROUTES_WEB=\"http://${PETSHOP_ROUTES_WEB_HOSTPORT}\"; fi; if [ -n \"${PETSHOP_ROUTES_CATALOG_HOSTPORT:-}\" ] && [ -z \"${PETSHOP_ROUTES_CATALOG:-}\" ]; then export PETSHOP_ROUTES_CATALOG=\"http://${PETSHOP_ROUTES_CATALOG_HOSTPORT}\"; fi; if [ -n \"${PETSHOP_ROUTES_COMMERCE_HOSTPORT:-}\" ] && [ -z \"${PETSHOP_ROUTES_COMMERCE:-}\" ]; then export PETSHOP_ROUTES_COMMERCE=\"http://${PETSHOP_ROUTES_COMMERCE_HOSTPORT}\"; fi; exec java ${JAVA_OPTS:-} -jar /app/app.jar"]
