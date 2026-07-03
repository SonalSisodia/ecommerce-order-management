# E-Commerce Order Management System

## Overview

This project is a **microservices-based E-Commerce Order Management System** built using **Spring Boot**. It demonstrates how independent services can collaborate while remaining loosely coupled through REST APIs and an API Gateway.

The application follows a modular microservices architecture where each service owns its own responsibility and database. It also includes fault tolerance using **Resilience4j Circuit Breaker**, centralized request routing through **Spring Cloud Gateway**, API documentation using **Swagger/OpenAPI**, and comprehensive **unit & integration testing**.

---

# Architecture

```
                        Client
                           │
                           ▼
                    API Gateway
                           │
          ┌────────────────┼────────────────┐
          ▼                ▼                ▼
  Product Service    Order Service   Notification Service
```

---

# Microservices

## Product Service

**Responsibilities**

- Create Product
- Retrieve Product by is
- Retrieve all Products

**Database**

- `product_db`

**Port**

- `8081`

---

## Order Service

**Responsibilities**

- Create Order
- Retrieve Order by id
- Retrieve all Order
- Validate Product by calling Product Service
- Send notification through Notification Service

**Database**

- `order_db`

**Port**

- `8082`

---

## Notification Service

**Responsibilities**

- Receive notification requests
- Log notification details (simulating notification delivery)

**Port**

- `8083`

---

## API Gateway

Acts as the single entry point for all client requests.

**Port**

- `8080`

Configured Routes

```
/products/**
/orders/**
/notifications/**
```

---

# Technology Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Cloud Gateway
- PostgreSQL
- Hibernate
- Maven
- Lombok
- Swagger / OpenAPI
- Resilience4j
- JUnit 5
- Mockito
- MockMvc
- SLF4J Logging

---

# Design Decisions

## Why Microservices?

The application is divided into independent services so that each business capability can evolve independently.

**Benefits**

- Better scalability
- Independent deployment
- Loose coupling
- Clear separation of responsibilities

---

## Why API Gateway?

Instead of exposing multiple services directly to clients, all requests pass through a single gateway.

**Benefits**

- Centralized routing
- Simplified client communication
- Easy future integration with authentication (OAuth2/JWT)
- Reduced client complexity

---

## Why Separate Databases?

Each microservice owns its own database.

**Benefits**

- Service autonomy
- Independent schema evolution
- Loose coupling
- Improved scalability

---

## Why PostgreSQL?

PostgreSQL was selected because it is a robust relational database with excellent transactional support, ACID compliance, and strong integration with Spring Data JPA.

---

## Why Resilience4j?

The Order Service depends on the Product Service.

If the Product Service becomes unavailable, the Circuit Breaker prevents repeated failing requests and enables graceful failure instead of cascading failures.

---

# Assumptions

- Products are created before orders are placed.
- Notification Service currently simulates notifications by logging messages.
- Services communicate synchronously using REST APIs.
- Each service manages its own database independently.

---

# Trade-offs

### REST Communication

REST APIs were chosen for simplicity and readability.

For production-scale systems, asynchronous communication using Kafka or RabbitMQ would be preferable for better scalability and decoupling.

---

### Notification Service

The Notification Service currently logs notifications instead of sending emails or SMS.

This keeps the project lightweight while demonstrating service-to-service communication.

---

### Authentication

Authentication and authorization were intentionally kept out of the current implementation to maintain focus on the core microservices architecture.

Future enhancements include:

- OAuth2
- JWT Authentication
- Role-Based Authorization

---

# Project Structure

```
ecommerce-order-management
│
├── product-service
├── order-service
├── notification-service
├── api-gateway
│
├── pom.xml
└── README.md
```

---

# Running the Project

## Prerequisites

- Java 17
- Maven
- PostgreSQL

---

## Clone Repository

```bash
git clone https://github.com/SonalSisodia/ecommerce-order-management.git
```

---

## Create Databases

Create the following PostgreSQL databases:

```
product_db
order_db
```

---

## Start Services

Run the services in the following order:

1. Product Service
2. Notification Service
3. Order Service
4. API Gateway

---

# API Endpoints

## Product Service

| Method | Endpoint |
|---------|----------|
| POST | `/products` |
| GET | `/products` |
| GET | `/products/{id}` |

---

## Order Service

| Method | Endpoint |
|---------|----------|
| POST | `/orders` |
| GET | `/orders` |
| GET | `/orders/{id}` |

---

## Notification Service

| Method | Endpoint |
|---------|----------|
| POST | `/notifications` |

---

# Swagger Documentation

Product Service

```
http://localhost:8081/swagger-ui/index.html
```

Order Service

```
http://localhost:8082/swagger-ui/index.html
```

Notification Service

```
http://localhost:8083/swagger-ui/index.html
```

---

# Testing

The project contains both **Unit Tests** and **Integration Tests**.

### Unit Testing

- Service Layer
- Controller Layer

### Integration Testing

- End-to-End API Testing using MockMvc
- Spring Boot Integration Tests

### Testing Tools

- JUnit 5
- Mockito
- MockMvc

---

# Future Enhancements

- OAuth2 Authentication
- JWT Authorization

---

# Author

**Sonal Sisodia**

Backend Developer | Java | Spring Boot | Microservices