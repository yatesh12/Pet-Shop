# Pet Shop Platform

Pet Shop is split into two top-level folders now:

- `frontend/` for the customer-facing Thymeleaf app
- `backend/` for the gateway and the microservices

The business logic stays the same. This wrap just makes the repository easier to deploy, easier to reason about, and easier to hand off to Render and Neon.

## What Runs Where

- `backend/petshop-gateway`: public entrypoint
- `backend/petshop-catalog-service`: catalog, pets, products, services, blog, FAQ, promotions, team
- `backend/petshop-commerce-service`: cart, checkout, orders, bookings, inquiries, wishlist, adoption requests
- `backend/petshop-shared`: DTOs and enums shared across services
- `frontend/petshop-web`: storefront, auth, account, and admin UI

## Runtime Model

- Gateway is the public URL.
- Web app talks to catalog and commerce via REST.
- All stateful services use the same Neon PostgreSQL database.
- Asset URLs can be stored directly as normal HTTP URLs in the existing `imageUrl` fields.

## Local Environment Variables

Use these values in `.env`, shell, VS Code launch settings, or Render variables:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>?sslmode=require
SPRING_DATASOURCE_USERNAME=<user>
SPRING_DATASOURCE_PASSWORD=<password>
PETSHOP_SERVICES_CATALOG_BASE_URL=http://localhost:8081
PETSHOP_SERVICES_COMMERCE_BASE_URL=http://localhost:8082
PETSHOP_ROUTES_WEB=http://localhost:8080
PETSHOP_ROUTES_CATALOG=http://localhost:8081
PETSHOP_ROUTES_COMMERCE=http://localhost:8082
```

## Local Run Order

1. Start `petshop-catalog-service`
2. Start `petshop-commerce-service`
3. Start `petshop-web`
4. Start `petshop-gateway`

Gateway URL locally:

- `http://localhost:8090`

## Production Path

The recommended production setup is:

1. Neon for PostgreSQL
2. Render for hosting the services
3. `petshop-gateway` as the public entrypoint

Full step-by-step notes are in:

- [`docs/production-deployment.md`](docs/production-deployment.md)
- [`backend/README.md`](backend/README.md)
- [`frontend/README.md`](frontend/README.md)

Render blueprints in the repo:

- [`render-free.yaml`](render-free.yaml) for the free-tier setup
- [`render-backend.yaml`](render-backend.yaml) for the paid/private-service setup

## Verification Checklist

- Home page loads with the new modern hero and section cards
- Shop, pets, services, blog, FAQ, and contact pages render through the gateway
- Registration and login work
- Cart, checkout, bookings, inquiries, and adoptions work end to end
- Admin dashboard, catalog, operations, and users pages work
- Public HTTP image URLs render correctly

## Note

I could not run Maven in this environment because `mvn` is not installed here. The project is wired for Java 21 and should be built locally or on Render with a Java 21 image.
