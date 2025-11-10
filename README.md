# LocalMart - Local E-Commerce Platform

A full-stack e-commerce platform connecting local sellers with customers, built with Spring Boot and React.

## 🚀 Features

### For Customers
- Browse products from local sellers
- Search and filter products by category and price
- Add products to cart and checkout
- View order history
- User authentication and profile management

### For Sellers
- Register as a seller and create a store
- Add, edit, and delete products
- Upload product images
- Manage orders (approve/reject)
- View dashboard with sales statistics
- Track revenue and order counts

## 🛠️ Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **H2 Database** (File-based)
- **Maven**

### Frontend
- **React 18**
- **Vite**
- **Axios** (HTTP Client)
- **React Router** (Routing)
- **Lucide React** (Icons)
- **CSS Modules** (Styling)

## 📁 Project Structure

```
LocalMart/
├── backend/          # Spring Boot backend
│   ├── src/
│   │   └── main/
│   │       ├── java/  # Java source code
│   │       └── resources/
│   │           └── application.properties
│   ├── data/         # H2 database files
│   └── uploads/      # Product images
│
└── frontend/         # React frontend
    ├── src/
    │   ├── components/  # React components
    │   ├── pages/       # Page components
    │   ├── context/     # React contexts
    │   └── styles/      # Global styles
    └── public/          # Static assets
```

## 🚦 Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18+ and npm
- Maven (or use Maven wrapper)

### Backend Setup

1. Navigate to backend directory:
```bash
cd backend
```

2. Run the Spring Boot application:
```bash
# Using Maven wrapper (Windows)
./mvnw.cmd spring-boot:run

# Or using Maven (if installed)
mvn spring-boot:run
```

3. Backend will run on `http://localhost:8080`

### Frontend Setup

1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

4. Frontend will run on `http://localhost:5173`

## 🔐 Default Configuration

### Database
- **Type**: H2 (File-based)
- **Location**: `backend/data/localmart-db.mv.db`
- **Console**: `http://localhost:8080/h2-console`
- **Username**: `sa`
- **Password**: `password`

### JWT Secret
- Configured in `application.properties`
- Change `app.jwt.secret` for production

## 📝 API Endpoints

### Public Endpoints
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search?q={query}` - Search products
- `GET /api/sellers` - Get all sellers
- `POST /api/auth/register/customer` - Register customer
- `POST /api/auth/register/seller` - Register seller
- `POST /api/auth/login` - Login

### Protected Endpoints (Require Authentication)
- `POST /api/products` - Create product (Seller only)
- `PUT /api/products/{id}` - Update product (Seller only)
- `DELETE /api/products/{id}` - Delete product (Seller only)
- `GET /api/orders/seller` - Get seller orders
- `PUT /api/orders/{id}/approve` - Approve order (Seller only)
- `PUT /api/orders/{id}/reject` - Reject order (Seller only)

## 🎨 Features Implemented

✅ User Authentication (JWT)
✅ Product Management (CRUD)
✅ Shopping Cart
✅ Order Processing
✅ Seller Dashboard
✅ Order Approval/Rejection
✅ Product Image Upload
✅ Search and Filter
✅ Responsive UI

## 📸 Screenshots

(Add screenshots of your application here)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

Your Name

## 🙏 Acknowledgments

- Spring Boot team
- React team
- All open-source contributors

