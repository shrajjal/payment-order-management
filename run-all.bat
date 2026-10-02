@echo off
setlocal
cd /d "%~dp0"
echo Starting the complete Payment & Order Management System...
docker compose up --build
