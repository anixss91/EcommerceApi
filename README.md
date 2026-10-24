# 🛒 E-Commerce REST API

A production-ready, fully authenticated E-Commerce backend API built with Spring Boot. Features role-based access control, cart management, order checkout with stock management, global exception handling, DTO-driven data transfer, and strict input validation.

## 🚀 Live API

```
https://ecommerceapi-izib.onrender.com
```

## 💻 GitHub Repository

```
https://github.com/anixss91/EcommerceApi
```

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Security + JWT** — authentication and role-based authorization
- **Spring Data JPA + Hibernate** — database access and ORM
- **PostgreSQL** — relational database
- **Docker + Docker Compose** — containerization
- **Render** — cloud deployment
- **BCrypt** — password hashing

---

## ✨ Features

- ✅ User registration and login with JWT authentication
- ✅ Role-based access control (CUSTOMER / ADMIN)
- ✅ Full product and category management
- ✅ Shopping cart with add, update, remove, and clear
- ✅ Order checkout with automatic stock management
- ✅ Order history per user
- ✅ Global exception handling
- ✅ DTO pattern for clean data transfer
- ✅ Input validation
- ✅ Dockerized with Docker Compose
- ✅ Deployed and live on Render

---

## 🗄️ Database Schema

```
users
├── id, first_name, last_name, email, password, role

categories
├── id, name

products
├── id, name, description, price, stock_quantity, category_id

orders
├── id, status, total_price, created_at, user_id

order_items
├── id, quantity, price, order_id, product_id

cart
├── id, user_id

cart_items
├── id, quantity, cart_id, product_id
```

---

## 🔐 Authentication & Roles

This API uses JWT authentication. Include the token in every protected request:

```
Authorization: Bearer <your_token>
```

| Role | Access |
|------|--------|
| Public | Browse products and categories |
| CUSTOMER | Cart, checkout, order history |
| ADMIN | Create/edit/delete products and categories, manage all orders |

---

## 📡 API Endpoints

### Auth (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT token |

**Register body:**
```json
{
    "firstName": "Anis",
    "lastName": "Test",
    "email": "anis@gmail.com",
    "password": "123456"
}
```

**Login body:**
```json
{
    "email": "anis@gmail.com",
    "password": "123456"
}
```

---

### Categories

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/categories` | Public | Get all categories |
| GET | `/categories/{id}` | Public | Get category by ID |
| POST | `/categories` | Admin | Create category |
| DELETE | `/categories/{id}` | Admin | Delete category |

**Create category body:**
```json
{
    "name": "Electronics"
}
```

---

### Products

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/products` | Public | Get all products |
| GET | `/products/{id}` | Public | Get product by ID |
| GET | `/products/category/{categoryId}` | Public | Get products by category |
| POST | `/products` | Admin | Create product |
| PUT | `/products/{id}` | Admin | Update product |
| DELETE | `/products/{id}` | Admin | Delete product |

**Create product body:**
```json
{
    "name": "iPhone 15",
    "description": "Latest iPhone model",
    "price": 999.99,
    "stockQuantity": 50,
    "category": {
        "id": 1
    }
}
```

---

### Cart (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/cart/my-cart` | Get current user's cart |
| POST | `/cart/add/{productId}?quantity=2` | Add product to cart |
| PUT | `/cart/{cartItemId}?quantity=3` | Update item quantity |
| DELETE | `/cart/{cartItemId}` | Remove item from cart |
| DELETE | `/cart/clear` | Clear entire cart |

---

### Orders (Authenticated)

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/orders/checkout` | Customer | Checkout cart → creates order |
| GET | `/orders/my-orders` | Customer | Get current user's orders |
| GET | `/orders` | Admin | Get all orders |
| GET | `/orders/{id}` | Admin | Get order by ID |
| PATCH | `/orders/{id}/status` | Admin | Update order status |
| DELETE | `/orders/{id}` | Admin | Delete order |

**Update order status body:**
```json
{
    "status": "DELIVERED"
}
```

---

## 🛒 Checkout Flow

```
1. Register / Login → get JWT token
2. Browse products → GET /products
3. Add items to cart → POST /cart/add/{productId}?quantity=1
4. View cart → GET /cart/my-cart
5. Checkout → POST /orders/checkout
   - Creates order from cart items
   - Reduces product stock automatically
   - Clears cart after successful checkout
6. View orders → GET /orders/my-orders
```

---

## 🐳 Run Locally with Docker

### Prerequisites
- Docker Desktop installed and running
- Git

### Steps

**1. Clone the repository:**
```bash
git clone https://github.com/anixss91/EcommerceApi.git
cd EcommerceApi
```

**2. Create a `.env` file in the root directory:**
```env
DB_URL=jdbc:postgresql://db:5432/ecommerce_db
DB_USERNAME=postgres
DB_PASSWORD=yourpassword
```

**3. Build and run:**
```bash
docker-compose up --build
```

**4. API available at:**
```
http://localhost:8080
```

---

## 💻 Run Locally without Docker

### Prerequisites
- Java 21
- Maven
- PostgreSQL running locally

### Steps

**1. Clone the repository:**
```bash
git clone https://github.com/anixss91/EcommerceApi.git
cd EcommerceApi
```

**2. Create database in PostgreSQL:**
```sql
CREATE DATABASE ecommerce_db;
```

**3. Configure `application.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

**4. Run:**
```bash
./mvnw spring-boot:run
```

**5. API available at:**
```
http://localhost:8080
```

---

## 📦 DTOs (Data Transfer Objects)

The API uses DTOs to separate the internal data model from what is exposed externally, preventing over-exposure of sensitive fields and keeping responses clean.

| DTO | Purpose |
|-----|---------|
| `ProductRequestDTO` | Incoming data when creating/updating a product |
| `ProductResponseDTO` | Outgoing product data returned to client |
| `CategoryRequestDto` | Incoming data when creating a category |
| `CategoryResponseDto` | Outgoing category data returned to client |
| `AddToCartRequest` | Incoming data when adding item to cart |
| `CartItemResponseDTO` | Outgoing cart item data with product details |
| `CartResponseDTO` | Outgoing full cart with all items |
| `OrderItemResponseDTO` | Outgoing order item data |
| `OrderResponseDTO` | Outgoing full order with items and total |
| `UpdateOrderStatusRequest` | Incoming data when updating order status |

---

## ⚠️ Global Exception Handling

All errors are handled globally using `@ControllerAdvice` and return a consistent `ErrorResponse` JSON structure instead of raw Spring error pages.

**Error Response Format:**
```json
{
    "status": 404,
    "message": "Product not found",
    "timestamp": "2026-08-01T12:00:00"
}
```

| Exception | HTTP Status | When it triggers |
|-----------|-------------|-----------------|
| `ResourceNotFoundException` | 404 Not Found | Entity not found by ID |
| `BadRequestException` | 400 Bad Request | Invalid input or business rule violation |

---

## 📁 Project Structure

```
src/main/java/com/Eapi/scalable/
├── controller/
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── CategoryController.java
│   ├── CartController.java
│   └── OrderController.java
├── model/
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── LoginRequest.java
│   └── RegisterRequest.java
├── repository/
│   ├── UserRepo.java
│   ├── ProductRepo.java
│   ├── CategoryRepo.java
│   ├── OrderRepo.java
│   ├── OrderItemRepo.java
│   ├── CartRepo.java
│   └── CartItemRepo.java
├── service/
│   ├── ProductService.java
│   ├── CategoryService.java
│   ├── CartService.java
│   └── OrderService.java
├── dto/
│   ├── AddToCartRequest.java
│   ├── CartItemResponseDTO.java
│   ├── CartResponseDTO.java
│   ├── CategoryRequestDto.java
│   ├── CategoryResponseDto.java
│   ├── OrderItemResponseDTO.java
│   ├── OrderResponseDTO.java
│   ├── ProductRequestDTO.java
│   ├── ProductResponseDTO.java
│   └── UpdateOrderStatusRequest.java
├── exception/
│   ├── BadRequestException.java
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── security/
│   ├── JwtUtil.java
│   ├── JwtFilter.java
│   └── SecurityConfig.java
└── ScalableApplication.java
```

---

## 🔒 Security

- Passwords hashed with **BCrypt**
- JWT tokens expire after **24 hours**
- Public endpoints: browsing products and categories
- Protected endpoints: cart and orders require valid JWT
- Admin endpoints: require JWT with ADMIN role
- Stateless authentication — no sessions stored server-side

---

## 👨‍💻 Author

**Anis** — 3nd year CS student building toward a backend engineering career

- GitHub: [@anixss91](https://github.com/anixss91)
- Project 1: [SaaS Order Management API](https://github.com/anixss91/RESTApi)
- Project 2: [E-Commerce API](https://github.com/anixss91/EcommerceApi)
