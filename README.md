E-Commerce Order Management System
Overview
This project is a microservices-based e-commerce backend developed using Spring Boot.
The system demonstrates how independent services communicate with each other while remaining loosely coupled. It follows RESTful API principles and includes fault tolerance using Resilience4j and centralized routing using Spring Cloud Gateway.

Architecture
           Client 
            │            
            ▼
       API Gateway
            │ 
┌───────────┼────────────┐
▼           ▼            ▼
Product Service  Order Service  Notification Service

Services
**Product Service**
 Responsible for
 - Creating products
 - Updating products
 - Fetching products

 Database
 - product_db

 Port
 - 8081

---
**Order Service**
 Responsible for
 - Creating orders
 - Fetching orders
 - Calling Product Service
 - Calling Notification Service

 Database
 - order_db

 Port
 -8082

---
**Notification Service**
 Responsible for
 - Sending order notifications
 - (Current implementation logs notification messages.)

 Port
 - 8083

---
**API Gateway**
 Acts as a single entry point for all client requests.
 Port
 - 8080

 Configured routes
 - /products/**
 - /orders/**
 - /notifications/**

---
Technologies Used
 - Java 17
 - Spring Boot 3
 - Spring Data JPA
 - Spring Cloud Gateway
 - PostgreSQL
 - Hibernate
 - Maven
 - Lombok
 - Swagger/OpenAPI
 - JUnit 5
 - Mockito
 - MockMvc
 - Resilience4j
 - SLF4J Logging

---------------
Design Decisions

Why Microservices?
Microservices allow independent deployment and scalability of individual business capabilities such as Product, Order, and Notification services.

----
Why API Gateway?
The API Gateway provides a single entry point for all client requests and simplifies routing to backend services.
Benefits:
Centralized routing
Easier future authentication
Simplified client communication

----
Why Resilience4j?
Order Service depends on Product Service.
If Product Service becomes unavailable, the Circuit Breaker prevents repeated failing calls and allows the system to fail gracefully.

----
Why PostgreSQL?
PostgreSQL is an open-source relational database offering ACID compliance and excellent support for transactional applications.

----
Why Separate Databases?
Each microservice owns its own database.
Benefits:
Loose coupling
Independent deployment
Better scalability
Database autonomy
Assumptions
Product IDs already exist before placing an order.
Notification Service simulates sending notifications by logging messages.
Services communicate using REST APIs.

----
Trade-offs
REST instead of Messaging
REST communication was chosen for simplicity.
In production, asynchronous communication (Kafka/RabbitMQ) would be preferable for notifications.
Notification Service
Notifications are logged rather than sent via email or SMS to keep the implementation lightweight.

----
Future enhancement:
OAuth2
JWT


-----
**Running the Project**

Clone Repository
- git clone https://github.com/SonalSisodia/ecommerce-order-management.git

Start PostgreSQL
Create databases
- product_db
- order_db

Run Services 

Start services in the following order:

1. Product Service
2. Notification Service
3. Order Service
4. API Gateway


-----------API Endpoints----------
**Product Service**
POST /products

GET /products

GET /products/{id}

PUT /products/{id}
----
**Order Service**
POST /orders

GET /orders

GET /orders/{id}

----
**Notification Service**
POST /notifications

---
Swagger
Product Service
http://localhost:8081/swagger-ui/index.html
Order Service
http://localhost:8082/swagger-ui/index.html
Notification Service
http://localhost:8083/swagger-ui/index.html

-------
Testing
The project includes
Unit Tests
Controller Tests
Service Tests
Integration Tests

----
Tools
JUnit 5
Mockito
MockMvc