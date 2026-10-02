# Payment & Order Management System — All Phases Complete

This distribution is intended to be the final runnable version. There is no phase-by-phase implementation required after extraction.

## Phase 0 — Foundation
- Spring Boot 3.3.4 / Java 21
- PostgreSQL 16 via Docker Compose
- Redis 7 via Docker Compose
- Environment-driven configuration
- Health endpoint

## Phase 1 — User Management & JWT
- Registration and login
- BCrypt passwords
- USER / ADMIN roles
- JWT creation and validation
- Stateless Spring Security
- Protected user profile endpoint
- Authentication/validation exception handling

## Phase 2 — Product Management
- Product CRUD
- UUID identifiers
- Decimal prices
- Stock management
- Admin-only create/update/delete
- Product DTO validation

## Phase 3 — Order Management
- Create order from product IDs and quantities
- Server-side total calculation
- Stock validation and deduction
- Current-user order listing/detail
- Pending-order cancellation
- Stock restoration on cancellation
- Transactional service methods

## Phase 4 — Payment Simulation
- One payment per order
- Unique transaction ID
- Simulated successful payment
- Payment amount taken from the order total
- Order transitions to CONFIRMED after payment
- User ownership checks

## Phase 5 — Redis Caching
- Redis-backed Spring Cache
- Product list cache
- Product-by-ID cache
- TTL configuration
- Cache invalidation on product changes and order stock changes

## Phase 6 — Testing
- JUnit 5
- Mockito service tests
- Standalone MockMvc controller tests
- JWT tests
- Tests do not require PostgreSQL for controller/service unit tests

## Phase 7 — Dockerization
- Docker Compose for PostgreSQL and Redis
- Application Dockerfile
- `.dockerignore`
- Build/run scripts

## Phase 8 — Documentation
- README
- API reference
- Environment template
- Architecture and complete-phase notes
