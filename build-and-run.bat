@echo off
REM Parking Lot API - Build and Run Script for Windows
REM This script provides various options to build and run the application

setlocal enabledelayedexpansion

REM Function to check if Docker is running
:check_docker
docker info >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Docker is not running. Please start Docker and try again.
    exit /b 1
)
goto :eof

REM Function to build the application
:build_app
echo [INFO] Building Spring Boot application...
call mvn clean package -DskipTests
if errorlevel 1 (
    echo [ERROR] Build failed!
    exit /b 1
)
echo [SUCCESS] Application built successfully!
goto :eof

REM Function to run tests
:run_tests
echo [INFO] Running tests...
call mvn test
if errorlevel 1 (
    echo [ERROR] Tests failed!
    exit /b 1
)
echo [SUCCESS] Tests completed!
goto :eof

REM Function to build Docker image
:build_docker
call :check_docker
echo [INFO] Building Docker image...
docker build -t parking-lot-api:latest .
if errorlevel 1 (
    echo [ERROR] Docker build failed!
    exit /b 1
)
echo [SUCCESS] Docker image built successfully!
goto :eof

REM Function to run with Docker Compose (full stack)
:run_full_stack
call :check_docker
echo [INFO] Starting full stack with Docker Compose...
docker-compose up -d
echo [INFO] Waiting for services to be ready...
timeout /t 30 /nobreak >nul
echo [SUCCESS] Full stack is running!
echo [INFO] API: http://localhost:8080
echo [INFO] Swagger UI: http://localhost:8080/swagger-ui.html
echo [INFO] Database: localhost:5432 (parking_db)
echo [INFO] Redis: localhost:6379
goto :eof

REM Function to run development environment
:run_dev_env
call :check_docker
echo [INFO] Starting development environment...
docker-compose -f docker-compose.dev.yml up -d
echo [SUCCESS] Development environment is running!
echo [INFO] Database: localhost:5433 (parking_dev)
echo [INFO] Redis: localhost:6380
echo [WARNING] Run the Spring Boot app locally with: mvn spring-boot:run
goto :eof

REM Function to stop all services
:stop_services
call :check_docker
echo [INFO] Stopping all services...
docker-compose down
docker-compose -f docker-compose.dev.yml down
echo [SUCCESS] All services stopped!
goto :eof

REM Function to clean up everything
:cleanup
call :check_docker
echo [INFO] Cleaning up Docker resources...
docker-compose down -v
docker-compose -f docker-compose.dev.yml down -v
docker system prune -f
echo [SUCCESS] Cleanup completed!
goto :eof

REM Function to show logs
:show_logs
call :check_docker
echo [INFO] Showing application logs...
docker-compose logs -f parking-api
goto :eof

REM Function to run only the API container
:run_api_only
call :check_docker
call :build_docker
echo [INFO] Running API container only...
docker run -d --name parking-api-standalone -p 8080:8080 --env-file .env parking-lot-api:latest
echo [SUCCESS] API container is running on http://localhost:8080
goto :eof

REM Function to show help
:show_help
echo Parking Lot API - Build and Run Script
echo.
echo Usage: %0 [OPTION]
echo.
echo Options:
echo   build           Build the Spring Boot application
echo   test            Run tests
echo   docker-build    Build Docker image
echo   full-stack      Run complete application with database and cache
echo   dev-env         Run development environment (DB + Redis only)
echo   api-only        Run only the API container
echo   stop            Stop all running services
echo   logs            Show application logs
echo   cleanup         Stop services and clean up Docker resources
echo   help            Show this help message
echo.
echo Examples:
echo   %0 full-stack   # Start everything
echo   %0 dev-env      # Start DB and Redis for local development
echo   %0 stop         # Stop all services
goto :eof

REM Main script logic
if "%1"=="build" (
    call :build_app
) else if "%1"=="test" (
    call :run_tests
) else if "%1"=="docker-build" (
    call :build_docker
) else if "%1"=="full-stack" (
    call :run_full_stack
) else if "%1"=="dev-env" (
    call :run_dev_env
) else if "%1"=="api-only" (
    call :run_api_only
) else if "%1"=="stop" (
    call :stop_services
) else if "%1"=="logs" (
    call :show_logs
) else if "%1"=="cleanup" (
    call :cleanup
) else (
    call :show_help
)