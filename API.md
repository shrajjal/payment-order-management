# API Reference

Base URL: `http://localhost:8080`

All protected endpoints use:

`Authorization: Bearer <JWT>`

## Health

`GET /api/health` — public.

## Authentication

`POST /api/auth/register` — public.

```json
{"name":"John","email":"john@example.com","password":"password123"}
```

`POST /api/auth/login` — public.

```json
{"email":"john@example.com","password":"password123"}
```

`GET /api/users/me` — authenticated.

## Products

`GET /api/products` — authenticated.

`GET /api/products/{id}` — authenticated.

`POST /api/products` — ADMIN.

```json
{"name":"Keyboard","description":"Mechanical keyboard","price":2999.00,"stock":20}
```

`PUT /api/products/{id}` — ADMIN.

`DELETE /api/products/{id}` — ADMIN.

## Orders

`POST /api/orders` — authenticated.

```json
{"items":[{"productId":"PRODUCT_UUID","quantity":2}]}
```

The server calculates the total and decrements stock in one transaction.

`GET /api/orders` — authenticated, current user's orders.

`GET /api/orders/{id}` — authenticated, current user's order only.

`PUT /api/orders/{id}/cancel` — authenticated; only pending orders can be cancelled. Stock is restored.

## Payments

`POST /api/payments/{orderId}?paymentMethod=SIMULATED` — authenticated.

Creates a simulated successful payment and changes the order to `CONFIRMED`.

`GET /api/payments/{orderId}` — authenticated, current user's payment only.

## Default development admin

Email: `admin@example.com`

Password: `Admin@12345`

Change the seeded credentials before any non-local use.
