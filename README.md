# Parking Lot Reservation System - Enterprise Edition

A high-performance, scalable Spring Boot REST API for managing parking lot reservations with enterprise-grade features including authentication, rate limiting, caching, and comprehensive security.

## 🚀 Features

### Core Features
- **Floor Management**: Create and manage parking floors
- **Slot Management**: Create parking slots with vehicle type support (2-wheeler, 4-wheeler)
- **Reservation System**: Book slots for specific time periods with conflict prevention
- **Availability Check**: Find available slots for given time ranges with caching
- **Cost Calculation**: Automatic pricing based on vehicle type and duration

### Enterprise Features
- **JWT Authentication**: Secure user authentication with role-based access control
- **Rate Limiting**: API throttling with 20 requests per minute per IP
- **Caching**: Multi-level caching with Caffeine for optimal performance
- **Database Optimization**: Strategic indexing and connection pooling
- **Security**: Role-based access control (Admin/Customer roles)
- **API Documentation**: Swagger/OpenAPI 3.0 documentation
- **Validation**: Comprehensive input validation and business rule enforcement
- **Exception Handling**: Global exception handling with proper error responses
- **Optimistic Locking**: Concurrent booking protection

## 🛠 Tech Stack

- **Java 17** - Latest LTS version
- **Spring Boot 3.5.6** - Latest Spring Boot
- **Spring Security** - JWT-based authentication
- **Spring Data JPA** - Database operations with optimizations
- **PostgreSQL** - Production-ready database
- **Caffeine Cache** - High-performance caching
- **Bucket4j** - Rate limiting
- **JWT (JJWT)** - JSON Web Token implementation
- **Swagger/OpenAPI** - API documentation
- **Lombok** - Code generation
- **Bean Validation** - Input validation
- **Maven** - Build tool

## Business Rules

1. Start time must be before end time
2. Reservation duration cannot exceed 24 hours
3. Vehicle number must match format XX00XX0000 (e.g., KA05MH1234)
4. Partial hours are charged as full hours
5. No overlapping reservations for the same slot

## Pricing

- **4-Wheeler**: ₹30 per hour
- **2-Wheeler**: ₹20 per hour

## 🚀 Quick Start Guide

### Prerequisites
- **Java 17+** - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Maven 3.6+** - Download from [Apache Maven](https://maven.apache.org/download.cgi)
- **Git** - For cloning the repository
- **PostgreSQL** (optional - using provided cloud database)

### Step 1: Clone Repository

```bash
git clone <your-repository-url>
cd parking
```

### Step 2: Environment Configuration

The `.env` file is already configured with a working database. For production, update these values:

```env
# Database Configuration
DATABASE_URL=jdbc:postgresql://localhost:5432/parking_db
DATABASE_USERNAME=your_db_username
DATABASE_PASSWORD=your_db_password

# JWT Configuration
JWT_SECRET=your_secure_jwt_secret_key_here
JWT_EXPIRATION=86400000

# Server Configuration
SERVER_PORT=8080

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# Logging Level
LOGGING_LEVEL=INFO
```

### Step 3: Build and Run

```bash
# Install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

### Step 4: Verify Installation

1. **Application**: http://localhost:8080
2. **Interactive API Docs**: http://localhost:8080/swagger
3. **Custom API Docs**: http://localhost:8080/api/docs

### Step 5: Test with Default Users

**Admin User:**
- Email: `admin@parking.com`
- Password: `Admin@123`

**Customer User:**
- Email: `john.doe@example.com`
- Password: `Password123!`

### Alternative: Using Docker

```bash
# Build and run with Docker
docker build -t parking-lot-api .
docker run -p 8080:8080 --env-file .env parking-lot-api
```

## 📊 Performance Optimizations

### Database Optimizations
- **Connection Pooling**: HikariCP with optimized settings (20 max connections)
- **Strategic Indexing**: Indexes on frequently queried columns
- **Batch Processing**: Hibernate batch operations enabled
- **Query Optimization**: Efficient JPA queries with proper joins

### Caching Strategy
- **Application Cache**: Caffeine cache for frequently accessed data
- **Cache Keys**: Strategic cache keys for floors, slots, and availability
- **TTL**: 5-minute cache expiration for optimal data freshness

### Rate Limiting
- **Bucket4j**: Token bucket algorithm
- **Limits**: 20 requests per minute per IP address
- **Graceful Degradation**: Proper error responses for rate limit exceeded

### Security Enhancements
- **JWT Tokens**: Stateless authentication with 24-hour expiration
- **Password Encryption**: BCrypt with salt
- **Role-based Access**: Admin and Customer roles with method-level security
- **CORS Configuration**: Configurable cross-origin resource sharing

## 🔗 API Endpoints

### Authentication (Public)
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/login` - Authenticate user and get JWT token

### Floors (Admin Only)
- `POST /api/floors` - Create a new floor
- `GET /api/floors` - Get all floors (cached)

### Slots (Admin Only)
- `POST /api/slots` - Create a new parking slot
- `GET /api/slots` - Get all slots

### Reservations (Authenticated Users)
- `POST /api/reserve` - Create a new reservation
- `GET /api/reservations/{id}` - Get reservation details (owner or admin only)
- `DELETE /api/reservations/{id}` - Cancel a reservation (owner or admin only)

### Availability (Authenticated Users)
- `POST /api/availability` - Check available slots for time range (cached)

### Documentation
- `GET /swagger-ui.html` - Swagger UI documentation
- `GET /api-docs` - OpenAPI 3.0 specification

## 📝 Sample API Calls

### 1. User Registration
```bash
POST /api/auth/signup
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "Password@123",
  "phoneNumber": "9876543210"
}
```

### 2. User Login
```bash
POST /api/auth/login
{
  "email": "john.doe@example.com",
  "password": "Password@123"
}
```

### 3. Create Floor (Admin Only)
```bash
POST /api/floors
Authorization: Bearer <admin-jwt-token>
{
  "name": "Ground Floor",
  "floorNumber": 1
}
```

### 4. Create Slot (Admin Only)
```bash
POST /api/slots
Authorization: Bearer <admin-jwt-token>
{
  "slotNumber": "A1",
  "vehicleType": "FOUR_WHEELER",
  "floorId": 1
}
```

### 5. Make Reservation
```bash
POST /api/reserve
Authorization: Bearer <user-jwt-token>
{
  "slotId": 1,
  "vehicleNumber": "KA05MH1234",
  "startTime": "2025-09-24T10:00:00",
  "endTime": "2025-09-24T12:00:00"
}
```

### 6. Check Availability
```bash
POST /api/availability
Authorization: Bearer <user-jwt-token>
{
  "startTime": "2025-09-24T10:00:00",
  "endTime": "2025-09-24T12:00:00",
  "floorId": 1
}
```

## Postman Collection

Import the `Parking_Lot_API.postman_collection.json` file into Postman for easy API testing.

## Testing

Run unit tests:
```bash
mvn test
```

## Error Handling

The API provides comprehensive error handling with appropriate HTTP status codes:

- `400 Bad Request` - Validation errors, invalid business rules
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource already exists, slot not available
- `500 Internal Server Error` - Unexpected errors

## Vehicle Number Format

Vehicle numbers must follow the Indian vehicle registration format: `XX00XX0000`
- First 2 characters: State code (letters)
- Next 2 characters: District code (numbers)  
- Next 2 characters: Series code (letters)
- Last 4 characters: Unique number (numbers)

Example: `KA05MH1234`
## 🔐 Aut
hentication & Authorization

### User Roles
- **ADMIN**: Can manage floors and slots, view all reservations
- **CUSTOMER**: Can make reservations, view own reservations

### JWT Token
- **Expiration**: 24 hours
- **Header**: `Authorization: Bearer <token>`
- **Claims**: User ID, email, role

### Password Requirements
- Minimum 8 characters
- At least one uppercase letter
- At least one lowercase letter  
- At least one digit
- At least one special character

## 📈 Monitoring & Performance

### Caching Metrics
- Cache hit/miss ratios available via actuator endpoints
- Cache eviction policies configured for optimal memory usage

### Rate Limiting
- 20 requests per minute per IP address
- Configurable limits in application properties
- Graceful error handling with proper HTTP status codes

### Database Performance
- Connection pool monitoring
- Query performance optimization
- Strategic indexing on high-traffic columns

## 🧪 Testing

### Unit Tests
Run unit tests with coverage:
```bash
mvn test
```

### Integration Testing with Postman
1. Import the `Parking_Lot_API.postman_collection.json` file
2. Set up environment variables:
   - `baseUrl`: http://localhost:8080
   - `authToken`: (auto-populated after login)
   - `adminToken`: (auto-populated after admin login)

### Test Scenarios
1. **Authentication Flow**: Signup → Login → Get Token
2. **Admin Operations**: Create floors and slots
3. **Customer Operations**: Check availability → Make reservation
4. **Rate Limiting**: Multiple rapid requests to test throttling
5. **Security**: Access protected endpoints without token

## 🚀 Production Deployment

### Environment Variables
```bash
DATABASE_URL=jdbc:postgresql://your_host:5432/your_database
DATABASE_USERNAME=your_db_username
DATABASE_PASSWORD=your_db_password
JWT_SECRET=your_secure_jwt_secret_key
REDIS_HOST=your_redis_host
REDIS_PORT=6379
```

### Performance Tuning
- Adjust connection pool sizes based on load
- Configure cache sizes based on memory availability
- Set appropriate rate limits for your use case
- Enable database query logging in development only

### Security Checklist
- [ ] Change default admin password
- [ ] Use strong JWT secret in production
- [ ] Enable HTTPS
- [ ] Configure CORS properly
- [ ] Set up proper logging and monitoring
- [ ] Regular security updates

## 📊 API Rate Limits

| Endpoint Category | Limit | Window |
|------------------|-------|---------|
| Authentication | 5 requests | 1 minute |
| General API | 20 requests | 1 minute |
| Admin Operations | 50 requests | 1 minute |

## 🐳 Docker Deployment

### Quick Start with Docker

```bash
# Build the Docker image
docker build -t parking-lot-api .

# Run with environment file
docker run -p 8080:8080 --env-file .env parking-lot-api

# Or run with inline environment variables
docker run -p 8080:8080 \
  -e DATABASE_URL="jdbc:postgresql://your-db-host:5432/parking_db" \
  -e DATABASE_USERNAME="your_db_username" \
  -e DATABASE_PASSWORD="your_db_password" \
  -e JWT_SECRET="your_secure_jwt_secret_key" \
  parking-lot-api
```

### Docker Compose (Complete Setup)

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  parking-api:
    build: .
    ports:
      - "8080:8080"
    environment:
      - DATABASE_URL=jdbc:postgresql://postgres-db:5432/parking_db
      - DATABASE_USERNAME=your_db_username
      - DATABASE_PASSWORD=your_db_password
      - JWT_SECRET=your_secure_jwt_secret_key
      - JWT_EXPIRATION=86400000
      - REDIS_HOST=redis-cache
      - REDIS_PORT=6379
    depends_on:
      - postgres-db
      - redis-cache
    networks:
      - parking-network

  postgres-db:
    image: postgres:15-alpine
    environment:
      - POSTGRES_DB=parking_db
      - POSTGRES_USER=your_db_username
      - POSTGRES_PASSWORD=your_db_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - parking-network

  redis-cache:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    networks:
      - parking-network

volumes:
  postgres_data:

networks:
  parking-network:
    driver: bridge
```

### Run with Docker Compose

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f parking-api

# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## 🔧 Configuration

### Application Properties
Key configurations in `application.properties`:
- Database connection pooling
- JWT token settings
- Cache configuration
- Rate limiting settings
- Security settings

### Custom Configurations
- Modify rate limits in `RateLimitingService`
- Adjust cache TTL in `CacheConfig`
- Update JWT expiration in properties file

## 📞 Support

For issues and questions:
1. Check the API documentation at `/swagger-ui.html`
2. Review the Postman collection for examples
3. Check application logs for detailed error information

## 🎯 Future Enhancements

- [ ] Real-time notifications for reservation updates
- [ ] Payment integration
- [ ] Mobile app support
- [ ] Analytics dashboard
- [ ] Multi-tenant support
- [ ] Automated parking slot detection
- [ ] Integration with parking sensors