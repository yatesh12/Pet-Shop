# Backend Workspace

`backend/` contains the runtime services and shared contracts.

## Modules

- `petshop-shared`
- `petshop-gateway`
- `petshop-catalog-service`
- `petshop-commerce-service`

## Production Notes

- The gateway is the only public service.
- Catalog, commerce, and web can all point at the same Neon PostgreSQL database.
- Use normal HTTP image URLs in the existing image fields instead of changing the service logic.

## Environment Example

See [`backend/.env.example`](/c:/Users/yetes/OneDrive/Desktop/Pet%20Shop/backend/.env.example).
