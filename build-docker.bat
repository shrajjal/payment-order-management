@echo off
call mvn clean package -DskipTests
if errorlevel 1 exit /b 1
docker compose up -d
if errorlevel 1 exit /b 1
docker build -t payment-order-management:1.0.0 .
docker run --rm --name payment-order-app --network host payment-order-management:1.0.0
