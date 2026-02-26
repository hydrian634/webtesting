# QanTum Spring Boot MVC Project

## Project Overview

This is a Spring Boot e-commerce application built following the **MVC (Model-View-Controller)** pattern, inspired by the Figma design from the QanTum project. The application includes user authentication, product management, shopping features, and a customer dashboard.

## Project Structure

### 📁 Models (`/src/main/java/com/webtesting/demo/model/`)

- **User.java** - User account information with authentication
- **Product.java** - Product catalog items
- **Address.java** - Customer shipping/billing addresses
- **Order.java** - Customer orders with status tracking
- **OrderItem.java** - Individual items in orders

### 📁 Repositories (`/src/main/java/com/webtesting/demo/repository/`)

- **UserRepository** - User data access operations
- **ProductRepository** - Product search and filtering
- **AddressRepository** - Address management
- **OrderRepository** - Order tracking and history
- **OrderItemRepository** - Order item details

### 📁 Services (`/src/main/java/com/webtesting/demo/service/`)

- **AuthService** - User authentication and registration
- **UserService** - User management (existing, enhanced)
- **ProductService** - Product catalog and search
- **AddressService** - Address management for users
- **OrderService** - Order processing and tracking

### 📁 Controllers (`/src/main/java/com/webtesting/demo/controller/`)

- **AuthController** - Sign in/up pages and form handling
- **ProductController** - Product listing, filtering, and details
- **DashboardController** - User dashboard and account pages
- **PageController** - Software and Support pages
- **UserController** - User API endpoints (existing, enhanced)

### 📁 DTOs (`/src/main/java/com/webtesting/demo/dto/`)

- **ProductResponse** - Product data transfer object
- **OrderResponse** - Order information
- **OrderItemResponse** - Order item details
- **AddressResponse** - Address information
- **LoginRequest** - Login form validation
- **SignUpRequest** - Registration form validation

### 📁 Templates (`/src/main/resources/templates/`)

#### Authentication Pages

- **signin.html** - User login page
- **signup.html** - User registration page

#### Product Pages

- **products.html** - Product listing with filters and pagination
- **product-detail.html** - Individual product details with tabs

#### Dashboard Pages

- **dashboard.html** - Main account dashboard
- **account-info.html** - User account information
- **address-book.html** - Saved shipping addresses
- **my-orders.html** - Order history and status

#### Other Pages

- **software.html** - Software/enterprise products page
- **support.html** - Customer support and FAQs

### 📁 Static Assets

- **css/style.css** - Global styles and utility classes
- **js/script.js** - JavaScript utilities and functions

### 📁 Configuration

- **SecurityConfig.java** - Spring Security configuration for MVC pages

---

## Key Features

### 1. **User Authentication**

- Sign up with email, username, password
- Sign in with email and password
- Password encryption using BCrypt
- Session management

### 2. **Product Management**

- Browse products with pagination
- Filter by category (Gaming Gear, Enterprise, etc.)
- Search products by keyword
- Product detail pages with specifications

### 3. **User Dashboard**

- View account information
- Manage shipping addresses
- Track order history
- View order status

### 4. **Customer Support**

- Frequently Asked Questions (FAQs)
- Order tracking
- Customer service information

---

## Database Schema

### Users Table

```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(100) UNIQUE NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(200),
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);
```

### Products Table

```sql
CREATE TABLE products (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10, 2) NOT NULL,
  category VARCHAR(100),
  image_url VARCHAR(500),
  stock_quantity INT,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);
```

### Orders Table

```sql
CREATE TABLE orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  address_id BIGINT,
  order_number VARCHAR(100) UNIQUE NOT NULL,
  status VARCHAR(50) DEFAULT 'PENDING',
  total_amount DECIMAL(10, 2) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(id),
  FOREIGN KEY(address_id) REFERENCES addresses(id)
);
```

### Addresses Table

```sql
CREATE TABLE addresses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  address VARCHAR(255) NOT NULL,
  detail_address VARCHAR(255),
  city VARCHAR(100),
  state VARCHAR(100),
  postal_code VARCHAR(20),
  country VARCHAR(100),
  is_default BOOLEAN DEFAULT false,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(id)
);
```

---

## API Endpoints

### Authentication

- `GET /signin` - Sign in page
- `POST /signin` - Process sign in
- `GET /signup` - Registration page
- `POST /signup` - Process registration

### Products

- `GET /products` - Product listing
- `GET /products/{id}` - Product detail

### Dashboard

- `GET /dashboard` - Main dashboard
- `GET /dashboard/account` - Account information
- `GET /dashboard/addresses` - Address book
- `GET /dashboard/orders` - My orders

### Pages

- `GET /software` - Software page
- `GET /support` - Support page

### REST API (for future mobile app)

- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user
- `GET /api/users` - Get all users

---

## Technologies Used

- **Framework**: Spring Boot 4.0.4
- **Database**: MariaDB
- **ORM**: Spring Data JPA (Hibernate)
- **Security**: Spring Security with BCrypt
- **Template Engine**: Thymeleaf
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Build Tool**: Maven

---

## Configuration

### Database Connection

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/quantum
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Running the Application

```bash
# Navigate to project directory
cd demo

# Build the project
mvn clean build

# Run the application
mvn spring-boot:run

# Application will be available at http://localhost:8080
```

---

## Design Patterns Used

1. **MVC Pattern** - Separation of Model, View, and Controller
2. **DTO Pattern** - Data Transfer Objects for API responses
3. **Service Layer** - Business logic encapsulation
4. **Repository Pattern** - Data access abstraction
5. **Builder Pattern** - Lombok @Builder for object creation

---

## Security Considerations

1. Passwords are encrypted using BCrypt
2. SQL injection protection through JPA parameterized queries
3. CORS configuration for API security
4. form-based authentication with Spring Security
5. Session management enabled

---

## Future Enhancements

1. JWT token-based authentication for REST APIs
2. Email verification for new accounts
3. Password reset functionality
4. Shopping cart system
5. Payment gateway integration
6. Product reviews and ratings
7. Wishlist feature
8. Admin panel for product management
9. Email notifications for orders
10. Mobile app support

---

## Author

Based on Figma design: https://www.figma.com/design/vo2c81vRY0JZkifEAFs93d/Qantum

Design created by: skyaliqo0206 with ChatGPT AI assistance

---

## License

All rights reserved 2026
