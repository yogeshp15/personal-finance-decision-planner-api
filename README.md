# Personal Finance Decision Planner API

A RESTful backend application developed using Java and Spring Boot to help users manage their personal finances, track income and expenses, monitor financial goals, and make informed financial decisions.

## Overview

The Personal Finance Decision Planner API provides a centralized backend system for managing financial activities and planning future purchases.

Unlike a basic expense tracker, this application combines income management, expense tracking, financial goal management, dashboard analytics, and financial decision simulation.

The application follows a layered architecture and exposes RESTful APIs that can be integrated with a frontend application.

## Features

### 1. User Authentication
- User registration and login.
- JWT-based authentication.
- Password encryption using BCrypt.
- Secured API endpoints using Spring Security.

### 2. Income Management
- Add new income records.
- Retrieve income records.
- Update existing income records.
- Delete income records.

### 3. Expense Management
- Add and manage expenses.
- Retrieve expense records.
- Update existing expenses.
- Delete expenses.

### 4. Financial Goal Management
- Create financial savings goals.
- Track savings progress.
- Monitor target amounts and remaining amounts.
- Manage existing financial goals.

### 5. Dashboard Analytics
- Retrieve financial summaries.
- Calculate total income and expenses.
- Monitor available balance.
- View monthly financial summaries.

### 6. Financial Decision Simulator
- Evaluate planned purchases.
- Analyze financial affordability.
- Calculate financial requirements using available financial information.
- Support better financial planning and decision-making.

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Backend programming language |
| Spring Boot | Backend application framework |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| Spring Data JPA | Database interaction |
| Hibernate | Object-relational mapping |
| MySQL | Relational database |
| Maven | Dependency management |
| Postman | API testing |
| Git & GitHub | Version control |

---

## Project Architecture

The application follows a layered architecture to separate responsibilities and maintain clean, organized code.

```text
src/
└── main/
    ├── java/
    │   └── com/finance/finance_planner/
    │       │
    │       ├── config/
    │       │
    │       ├── controller/
    │       │   ├── DashboardController.java
    │       │   ├── ExpenseController.java
    │       │   ├── GoalController.java
    │       │   ├── IncomeController.java
    │       │   ├── ScenarioController.java
    │       │   ├── TestController.java
    │       │   └── UserController.java
    │       │
    │       ├── dto/
    │       │   ├── DashboardResponseDTO.java
    │       │   ├── ExpenseRequestDTO.java
    │       │   ├── ExpenseResponseDTO.java
    │       │   ├── GoalRequestDTO.java
    │       │   ├── GoalResponseDTO.java
    │       │   ├── IncomeRequestDTO.java
    │       │   ├── IncomeResponseDTO.java
    │       │   ├── LoginRequestDTO.java
    │       │   ├── LoginResponseDTO.java
    │       │   ├── MonthlySummaryResponseDTO.java
    │       │   ├── RegisterRequestDTO.java
    │       │   ├── RegisterResponseDTO.java
    │       │   ├── ScenarioRequestDTO.java
    │       │   └── ScenarioResponseDTO.java
    │       │
    │       ├── entity/
    │       │   ├── ExpenseEntity.java
    │       │   ├── GoalEntity.java
    │       │   ├── IncomeEntity.java
    │       │   └── UserEntity.java
    │       │
    │       ├── exception/
    │       │   ├── BadRequestException.java
    │       │   ├── EmailAlreadyExistsException.java
    │       │   ├── GlobalExceptionHandler.java
    │       │   ├── InvalidEmailAndPassException.java
    │       │   └── ResourceNotFoundException.java
    │       │
    │       ├── repository/
    │       │   ├── ExpenseRepository.java
    │       │   ├── GoalRepository.java
    │       │   ├── IncomeRepository.java
    │       │   └── UserRepository.java
    │       │
    │       ├── security/
    │       │   ├── JwtAuthenticationFilter.java
    │       │   └── JwtService.java
    │       │
    │       ├── service/
    │       │   ├── DashboardService.java
    │       │   ├── ExpenseService.java
    │       │   ├── GoalService.java
    │       │   ├── IncomeService.java
    │       │   ├── ScenarioService.java
    │       │   └── UserService.java
    │       │
    │       └── FinancePlannerApplication.java
    │
    └── resources/
        └── application.properties
```

### Package Responsibilities

| Package | Responsibility |
|---|---|
| config | Application configuration |
| controller | Handles incoming HTTP requests |
| dto | Transfers data between client and server |
| entity | Defines database entities |
| exception | Handles application exceptions |
| repository | Performs database operations |
| security | Handles JWT authentication |
| service | Contains business logic |

---

## API Modules

The application provides the following API modules:

| Controller | Module |
|---|---|
| UserController | User registration and login |
| DashboardController | Financial dashboard and summaries |
| IncomeController | Income management |
| ExpenseController | Expense management |
| GoalController | Financial goal management |
| ScenarioController | Financial decision simulation |
| TestController | Testing endpoints |

### Authentication Endpoints

Base URL: `http://localhost:8080`

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate an existing user |

Authenticated endpoints use JWT bearer-token authentication.

*Refer to the individual controller classes for the complete route mappings and request/response formats.*

---

## Database

The application uses MySQL as its relational database.

**Database name:** `finance_planner`

The application uses Spring Data JPA and Hibernate for database persistence and entity management.

Main database entities:

- UserEntity
- IncomeEntity
- ExpenseEntity
- GoalEntity

---

## Getting Started

### Prerequisites

Make sure the following tools are installed:

- Java Development Kit (JDK 17)
- MySQL Server
- Maven
- IntelliJ IDEA (recommended)
- Postman (for API testing)

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/personal-finance-decision-planner-api.git
```

### 2. Navigate to the Project Directory

```bash
cd personal-finance-decision-planner-api
```

### 3. Create the MySQL Database

Open MySQL and execute:

```sql
CREATE DATABASE finance_planner;
```

### 4. Configure Database Credentials

Update your local `application.properties` with your MySQL database credentials and JWT configuration.

Do not commit actual database passwords or JWT secrets to GitHub.

### 5. Run the Application

Execute the following command:

```bash
mvn spring-boot:run
```

Alternatively, run `FinancePlannerApplication.java` from IntelliJ IDEA.

The application will start at:

```text
http://localhost:8080
```

---

## Security

The application incorporates:

- Spring Security for endpoint protection.
- JWT for stateless authentication.
- BCrypt for password hashing.
- Authentication filters for validating incoming JWT tokens.
- User-specific access to protected financial resources.

---

## API Testing

The REST APIs can be tested using Postman.

Testing includes:

- User registration and authentication.
- Income and expense operations.
- Financial goal management.
- Dashboard calculations.
- Financial decision simulation.
- JWT authentication and protected endpoints.

---

## Future Enhancements

- Swagger / OpenAPI API documentation.
- Automated unit and integration testing.
- Backend deployment.
- Additional financial analytics and reporting.

---

## Author

**Yogesh P.**

GitHub: https://github.com/yogeshp15

---

**Project:** Personal Finance Decision Planner API

**Built with:** Java 17, Spring Boot and MySQL.
