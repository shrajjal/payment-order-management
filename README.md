# Payment & Order Management System

Final all-phases implementation of a production-style Java backend for users, products, orders, payments, JWT security, PostgreSQL, Redis caching, testing, Docker and API documentation.

## Stack

- Java 21
- Spring Boot 3.3.4
- Spring MVC
- Spring Data JPA / Hibernate
- PostgreSQL 16
- Redis 7
- Spring Security + JWT (JJWT 0.12.6)
- BCrypt
- JUnit 5 + Mockito + MockMvc
- Docker / Docker Compose

## Architecture

```text
Client
  |
  v
Spring Security + JWT
  |
  v
REST Controllers
  |
  v
Service Layer / Transactions
  |                 \
  v                  v
PostgreSQL          Redis Cache
```

The application is a modular monolith. PostgreSQL is the source of truth; Redis is used for product read caching.

## Run everything — one command

### Requirements

- Docker Desktop

The final distribution is Docker-first. You do **not** need Maven installed just to run the complete system.

From the project directory run:

```bat
docker compose up --build
```

Or double-click/run:

```bat
run-all.bat
```

This builds the Java 21 application, runs the test suite during the image build, starts PostgreSQL and Redis, waits for both health checks, and then starts the API.

The project intentionally uses host ports **15432** for PostgreSQL and **16379** for Redis to avoid common Windows conflicts with 5432/6379. The application itself remains on port **8080**.

To stop the complete stack:

```bat
docker compose down
```

Or use `stop-all.bat`.

### Optional local-Maven workflow

If you already have JDK 21 and Maven 3.9+, you can also run `mvn clean test` followed by `mvn spring-boot:run`.

The API starts at `http://localhost:8080`.

Health:

```text
GET http://localhost:8080/api/health
```

## Default admin

```text
email: admin@example.com
password: Admin@12345
```

The admin is seeded automatically for local development. Change/remove the development credentials before non-local use.

## API flow

### Register

```http
POST /api/auth/register
Content-Type: application/json

{"name":"Test User","email":"testuser@gmail.com","password":"Test@123"}
```

### Login

```http
POST /api/auth/login
Content-Type: application/json

{"email":"testuser@gmail.com","password":"Test@123"}
```

Copy the returned JWT and use:

```http
Authorization: Bearer <JWT>
```

### Current user

```http
GET /api/users/me
Authorization: Bearer <JWT>
```

### Products

```text
GET    /api/products
GET    /api/products/{id}
POST   /api/products          ADMIN
PUT    /api/products/{id}     ADMIN
DELETE /api/products/{id}     ADMIN
```

Example product:

```json
{"name":"Keyboard","description":"Mechanical keyboard","price":2999.00,"stock":20}
```

### Orders

```text
POST /api/orders
GET  /api/orders
GET  /api/orders/{id}
PUT  /api/orders/{id}/cancel
```

Example:

```json
{"items":[{"productId":"PRODUCT_UUID","quantity":2}]}
```

The backend calculates the order total from current product prices and handles stock atomically inside the transaction.

### Payments

```text
POST /api/payments/{orderId}?paymentMethod=SIMULATED
GET  /api/payments/{orderId}
```

The payment is deliberately simulated; no external payment gateway is contacted. A successful simulation creates a transaction ID and confirms the order.

## Configuration

The included defaults are:

```text
DB_URL=jdbc:postgresql://localhost:15432/payment_order_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
REDIS_HOST=localhost
REDIS_PORT=16379
JWT_EXPIRATION_MS=3600000
SERVER_PORT=8080
PRODUCT_CACHE_TTL_SECONDS=300
```

Copy `.env.example` as a reference for environment configuration. Spring Boot environment variables can override all defaults.

## Docker application image

Build the application jar:

```bat
mvn clean package -DskipTests
```

Build the image:

```bat
docker build -t payment-order-management:1.0.0 .
```

The Dockerfile expects the jar at `target/payment-order-management-1.0.0.jar`.

## Project structure

```text
src/main/java/com/paymentorder
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
    └── impl

src/test/java/com/paymentorder
├── controller
├── security
└── service
```

## Complete phase checklist

See `PHASES-COMPLETE.md` for the implementation checklist and `API.md` for the endpoint reference.
