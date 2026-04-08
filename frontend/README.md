# Frontend Workspace

`frontend/` contains the customer-facing Thymeleaf web application.

## Module

- `petshop-web`

## Production Notes

- The web app is served privately behind the gateway in the recommended production setup.
- It talks to the catalog and commerce services over REST.
- Keep media assets in Supabase Storage and store their public URLs in the current `imageUrl` fields.

## Environment Example

See [`frontend/.env.example`](/c:/Users/yetes/OneDrive/Desktop/Pet%20Shop/frontend/.env.example).
