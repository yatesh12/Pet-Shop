# Production Deployment Guide

This guide keeps the microservice logic unchanged and focuses on configuration, hosting, and verification.

## 1. Final Layout

```text
Pet Shop/
|-- backend/
|   |-- petshop-shared/
|   |-- petshop-gateway/
|   |-- petshop-catalog-service/
|   |-- petshop-commerce-service/
|-- frontend/
|   |-- petshop-web/
|-- docs/
|-- docker-compose.yml
|-- pom.xml
|-- render.yaml
```

## 2. Supabase Setup

### Database

1. Create a Supabase project.
2. Open `Connect` in Supabase.
3. Use the PostgreSQL connection details for the app.
4. Put the JDBC URL in `SPRING_DATASOURCE_URL`.
5. Put the username in `SPRING_DATASOURCE_USERNAME`.
6. Put the password in `SPRING_DATASOURCE_PASSWORD`.
7. Keep `?sslmode=require` in the JDBC URL.

Recommended JDBC pattern:

```text
jdbc:postgresql://aws-0-REGION.pooler.supabase.com:5432/postgres?sslmode=require
```

Recommended username pattern:

```text
postgres.PROJECT_REF
```

### Object Storage

1. Create a public bucket in Supabase Storage.
2. Upload product, pet, service, team, and blog images.
3. Save the public URL in the existing `imageUrl` or `featuredImageUrl` columns.

Example public URL:

```text
https://PROJECT_REF.supabase.co/storage/v1/object/public/petshop-assets/products/carrier.svg
```

## 3. Render Setup

Recommended services:

- `petshop-catalog-service` as private
- `petshop-commerce-service` as private
- `petshop-web` as private
- `petshop-gateway` as public

The public traffic should go through the gateway.

## 4. Render Deployment Steps

1. Push the repo to GitHub.
2. In Render, create a Blueprint from the repo.
3. Use the root [`render.yaml`](../render.yaml).
4. Set the Supabase database env vars for the three stateful services.
5. Let Render build all services.
6. Expose only the gateway publicly.
7. Use the service URLs or blueprint wiring for internal traffic.

## 5. Manual Values You Must Configure

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `PETSHOP_SERVICES_CATALOG_BASE_URL` if you are not using blueprint wiring
- `PETSHOP_SERVICES_COMMERCE_BASE_URL` if you are not using blueprint wiring
- `PETSHOP_ROUTES_WEB` if you are not using blueprint wiring
- `PETSHOP_ROUTES_CATALOG` if you are not using blueprint wiring
- `PETSHOP_ROUTES_COMMERCE` if you are not using blueprint wiring

Optional:

- `JAVA_OPTS=-Xms256m -Xmx512m`
- `THYMELEAF_CACHE=true` in production

## 6. How Supabase Storage Fits Without Logic Changes

The app already stores raw image URLs. That means:

1. You upload an image to Supabase Storage.
2. You copy its public URL.
3. You paste the URL into the admin form or seed data.
4. The existing UI renders the image with no code change.

## 7. Local Smoke Test

Run locally with Docker:

```bash
docker compose up --build
```

Then verify:

1. Home page
2. Shop page
3. Pets page
4. Services page
5. Blog page
6. FAQ page
7. Contact page
8. Register and login
9. Cart and checkout
10. Booking and inquiry forms
11. Admin dashboard

## 8. Production Smoke Test

After Render deploys:

1. Open the gateway URL.
2. Check `/actuator/health`.
3. Browse the public storefront pages.
4. Register a user.
5. Add to cart and checkout.
6. Submit a booking and inquiry.
7. Log in as admin.
8. Confirm admin pages load.
9. Verify one image from Supabase Storage renders in production.
