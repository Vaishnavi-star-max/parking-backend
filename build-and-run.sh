#!/bin/bash

# Parking Lot API - Build and Run Script
# This script provides various options to build and run the application

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to check if Docker is running
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker and try again."
        exit 1
    fi
}

# Function to build the application
build_app() {
    print_status "Building Spring Boot application..."
    mvn clean package -DskipTests
    print_success "Application built successfully!"
}

# Function to run tests
run_tests() {
    print_status "Running tests..."
    mvn test
    print_success "Tests completed!"
}

# Function to build Docker image
build_docker() {
    check_docker
    print_status "Building Docker image..."
    docker build -t parking-lot-api:latest .
    print_success "Docker image built successfully!"
}

# Function to run with Docker Compose (full stack)
run_full_stack() {
    check_docker
    print_status "Starting full stack with Docker Compose..."
    docker-compose up -d
    print_status "Waiting for services to be ready..."
    sleep 30
    print_success "Full stack is running!"
    print_status "API: http://localhost:8080"
    print_status "Swagger UI: http://localhost:8080/swagger-ui.html"
    print_status "Database: localhost:5432 (parking_db)"
    print_status "Redis: localhost:6379"
}

# Function to run development environment
run_dev_env() {
    check_docker
    print_status "Starting development environment..."
    docker-compose -f docker-compose.dev.yml up -d
    print_success "Development environment is running!"
    print_status "Database: localhost:5433 (parking_dev)"
    print_status "Redis: localhost:6380"
    print_warning "Run the Spring Boot app locally with: mvn spring-boot:run"
}

# Function to stop all services
stop_services() {
    check_docker
    print_status "Stopping all services..."
    docker-compose down
    docker-compose -f docker-compose.dev.yml down
    print_success "All services stopped!"
}

# Function to clean up everything
cleanup() {
    check_docker
    print_status "Cleaning up Docker resources..."
    docker-compose down -v
    docker-compose -f docker-compose.dev.yml down -v
    docker system prune -f
    print_success "Cleanup completed!"
}

# Function to show logs
show_logs() {
    check_docker
    print_status "Showing application logs..."
    docker-compose logs -f parking-api
}

# Function to run only the API container
run_api_only() {
    check_docker
    build_docker
    print_status "Running API container only..."
    docker run -d \
        --name parking-api-standalone \
        -p 8080:8080 \
        --env-file .env \
        parking-lot-api:latest
    print_success "API container is running on http://localhost:8080"
}

# Function to show help
show_help() {
    echo "Parking Lot API - Build and Run Script"
    echo ""
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  build           Build the Spring Boot application"
    echo "  test            Run tests"
    echo "  docker-build    Build Docker image"
    echo "  full-stack      Run complete application with database and cache"
    echo "  dev-env         Run development environment (DB + Redis only)"
    echo "  api-only        Run only the API container"
    echo "  stop            Stop all running services"
    echo "  logs            Show application logs"
    echo "  cleanup         Stop services and clean up Docker resources"
    echo "  help            Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 full-stack   # Start everything"
    echo "  $0 dev-env      # Start DB and Redis for local development"
    echo "  $0 stop         # Stop all services"
}

# Main script logic
case "${1:-help}" in
    "build")
        build_app
        ;;
    "test")
        run_tests
        ;;
    "docker-build")
        build_docker
        ;;
    "full-stack")
        run_full_stack
        ;;
    "dev-env")
        run_dev_env
        ;;
    "api-only")
        run_api_only
        ;;
    "stop")
        stop_services
        ;;
    "logs")
        show_logs
        ;;
    "cleanup")
        cleanup
        ;;
    "help"|*)
        show_help
        ;;
esac