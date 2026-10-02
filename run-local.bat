@echo off
setlocal
set DB_URL=jdbc:postgresql://localhost:15432/payment_order_db
set DB_USERNAME=postgres
set DB_PASSWORD=postgres
set REDIS_HOST=localhost
set REDIS_PORT=16379
set JWT_SECRET=change-this-development-secret-key-to-a-long-random-value-please
set JWT_EXPIRATION_MS=3600000
set SERVER_PORT=8080
set PRODUCT_CACHE_TTL_SECONDS=300
call mvn clean test
if errorlevel 1 exit /b 1
call mvn spring-boot:run
