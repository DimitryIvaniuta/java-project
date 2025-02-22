# Java Spring Boot Backend

## Table of Contents
- [Table of Contents](#-table-of-contents)
- [Overview](#-overview)
- [️ Technologies Used](#️-technologies-used)
- [Project Structure](#-project-structure)
- [Authentication & Authorization](#-authentication--authorization)
- [API Endpoints](#-api-endpoints)
- [Security Configuration](#️-security-configuration)
- [Database Configuration](#️-database-configuration)
- [Running the Application](#-running-the-application)
- [Testing](#-testing)
- [Useful Commands](#-useful-commands)

## Overview
This is the backend part of the project built with **Java Spring Boot**. It handles user authentication using **OAuth2** and JWT, manages CORS settings, and serves as the API provider for the React frontend.

##️ Technologies Used
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **OAuth2**
- **JWT (JSON Web Tokens)**
- **Spring Data JPA**
- **H2/MySQL/PostgreSQL** (depending on configuration)
- **Gradle**
- **Lombok**

## Project Structure
```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── javaproject/
│   │   │           └── server/
│   │   │               ├── config/          # Security & CORS Configurations
│   │   │               ├── controller/      # REST Controllers
│   │   │               ├── data/
│   │   │               │   ├── entity/      # JPA Entities
│   │   │               │   └── repository/  # JPA Repositories
│   │   │               ├── dto/             # Data Transfer Objects
│   │   │               ├── exception/       # Custom Exceptions
│   │   │               ├── security/        # JWT Utilities & Security Filters
│   │   │               └── Application.java # Main Class
│   │   └── resources/
│   │       ├── application.yml              # App Configuration
│   │       └── static/                      # Static Resources
│   └── test/                                # Unit & Integration Tests
└── build.gradle                             # Gradle Build File
```

## Authentication & Authorization
- **OAuth2** is used for secure authentication.
- **JWT** is generated upon successful login.
- Passwords are hashed using **BCryptPasswordEncoder**.
- Security configuration ensures protected routes and role-based access.

## API Endpoints

### Authentication
- `POST /api/auth/login` - Authenticate user and return JWT.
- `POST /api/public/add-user` - Public endpoint to register new users.

### Users
- `GET /api/users` - Fetch all users (secured).
- `GET /api/users/{id}` - Fetch user by ID (secured).

## Security Configuration
- **Spring Security** is configured with custom JWT filters.
- CORS is enabled to allow requests from the React frontend (`http://localhost:4200`).
- CSRF protection is disabled for API usage.

## Database Configuration
- Configured using **Spring Data JPA**.
- Default in-memory **H2** database (for development).
- Supports **MySQL**/**PostgreSQL** in production.

### Example `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
  h2:
    console:
      enabled: true
```

## Running the Application
1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/backend.git
   cd backend
   ```

2. **Run using Gradle:**
   ```bash
   ./gradlew bootRun
   ```

3. **Access H2 Database Console:**
   ```
   http://localhost:5000/h2-console
   ```

4. **API is available at:**
   ```
   http://localhost:5000/api/
   ```

## Testing
Run unit and integration tests using:
```bash
./gradlew test
```

## Useful Commands
- **Build the project:**
  ```bash
  ./gradlew build
  ```

- **Run the app:**
  ```bash
  ./gradlew bootRun
  ```

- **Format code using Checkstyle:**
  ```bash
  ./gradlew check
  ```

- **Run tests:**
  ```bash
  ./gradlew test
  ```
  
---

## Contact

**Dzmitry Ivaniuta** — [diafter@gmail.com](mailto:diafter@gmail.com) — [GitHub](https://github.com/DimitryIvaniuta)
