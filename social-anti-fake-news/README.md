# Anti-Fake News System

A comprehensive full-stack application for detecting and managing fake news, built with Vue.js 3 frontend and Spring Boot backend.

## 🏗️ Architecture

### Frontend
- **Framework**: Vue.js 3 with Composition API
- **State Management**: Pinia
- **Routing**: Vue Router
- **Styling**: Tailwind CSS
- **Build Tool**: Vite

### Backend
- **Framework**: Spring Boot 3.2.0
- **Database**: H2 (development) / MySQL (production)
- **ORM**: Hibernate JPA
- **Security**: Spring Security with JWT
- **Documentation**: SpringDoc OpenAPI
- **Dependency Injection**: Spring IoC
- **Utilities**: Lombok

## 🚀 Features

### Frontend Features
- **Modern UI/UX**: Responsive design with Tailwind CSS
- **News Management**: View, search, and filter news articles
- **Voting System**: Vote on news authenticity
- **Comments**: Add comments with images
- **Statistics**: View news statistics and trends
- **Real-time Updates**: Dynamic content updates
- **Mobile Responsive**: Works on all device sizes

### Backend Features
- **RESTful API**: Complete CRUD operations
- **Authentication**: JWT-based authentication
- **Authorization**: Role-based access control
- **Data Validation**: Input validation and error handling
- **Database Integration**: JPA/Hibernate ORM
- **API Documentation**: Swagger/OpenAPI documentation
- **CORS Support**: Cross-origin resource sharing
- **Audit Logging**: Entity audit trails

## 📁 Project Structure

```
anti-fake-news/
├── frontend/                 # Vue.js frontend
│   ├── src/
│   │   ├── components/      # Vue components
│   │   ├── pages/          # Page components
│   │   ├── store/          # Pinia store
│   │   ├── router/         # Vue Router config
│   │   └── assets/         # Static assets
│   ├── public/             # Public assets
│   └── package.json        # Frontend dependencies
├── backend/                # Spring Boot backend
│   ├── src/main/java/
│   │   └── com/devspark/antifakenews/
│   │       ├── controller/ # REST controllers
│   │       ├── service/    # Business logic
│   │       ├── repository/ # Data access layer
│   │       ├── entity/     # JPA entities
│   │       ├── dto/        # Data transfer objects
│   │       ├── mapper/     # Entity-DTO mappers
│   │       └── config/     # Configuration classes
│   └── src/main/resources/
│       └── application.yml # Application configuration
└── docker-compose.yml      # Docker orchestration
```

## 🛠️ Technologies Used

### Frontend
- Vue.js 3.5.18
- Pinia 3.0.3
- Vue Router 4.5.1
- Tailwind CSS 4.1.12
- Vite 7.1.2
- NProgress 0.2.0

### Backend
- Spring Boot 3.2.0
- Spring Security 6.2.0
- Spring Data JPA 3.2.0
- Hibernate 6.2.0
- Lombok 1.18.30
- MapStruct 1.5.5
- SpringDoc OpenAPI 2.2.0
- JWT 0.11.5
- H2 Database 2.2.224
- MySQL Connector 8.0.33

## 🚀 Getting Started

### Prerequisites
- Node.js 18+ (for frontend)
- Java 17+ (for backend)
- Maven 3.6+ (for backend)
- Docker & Docker Compose (optional)

### Option 1: Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd anti-fake-news
   ```

2. **Start all services**
   ```bash
   docker-compose up --build
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080/api
   - API Documentation: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console

### Option 2: Manual Setup

#### Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

#### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

## 📚 API Documentation

The backend provides comprehensive API documentation through Swagger UI:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

### Main API Endpoints

#### News Management
- `GET /api/news` - Get all news (paginated)
- `GET /api/news/{id}` - Get news by ID
- `POST /api/news` - Create new news
- `PUT /api/news/{id}/status` - Update news status
- `DELETE /api/news/{id}` - Delete news
- `GET /api/news/search` - Search news
- `GET /api/news/status/{status}` - Get news by status
- `GET /api/news/trending` - Get trending news
- `GET /api/news/statistics` - Get news statistics

#### Comments
- `GET /api/comments/news/{newsId}` - Get comments for news
- `POST /api/comments` - Create comment
- `PUT /api/comments/{id}` - Update comment
- `DELETE /api/comments/{id}` - Delete comment

#### Voting
- `POST /api/votes` - Submit vote
- `GET /api/votes/news/{newsId}` - Get votes for news
- `DELETE /api/votes/news/{newsId}` - Remove vote

## 🗄️ Database Schema

### Entities
- **User**: System users with roles
- **News**: News articles with status
- **Comment**: User comments on news
- **Vote**: User votes on news authenticity

### Key Relationships
- User → News (One-to-Many)
- News → Comments (One-to-Many)
- News → Votes (One-to-Many)
- User → Comments (One-to-Many)
- User → Votes (One-to-Many)

## 🔒 Security

- **Authentication**: JWT-based token authentication
- **Authorization**: Role-based access control (USER, ADMIN, MODERATOR)
- **CORS**: Configured for cross-origin requests
- **Password Encryption**: BCrypt password hashing
- **Input Validation**: Comprehensive validation on all inputs

## 🧪 Testing

### Backend Testing
```bash
cd backend
./mvnw test
```

### Frontend Testing
```bash
cd frontend
npm run test
```

## 📦 Deployment

### Production Build

#### Backend
```bash
cd backend
./mvnw clean package -Pproduction
java -jar target/anti-fake-news-backend-1.0.0.jar
```

#### Frontend
```bash
cd frontend
npm run build
# Deploy the dist/ folder to your web server
```

### Docker Deployment
```bash
docker-compose -f docker-compose.prod.yml up --build
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

**DevSpark Team**
- Thanachai Naksomboon (662115020)
- Wanikkasit Nopthiraitthikun (652115043)
- Siwakorn Kankaewpab (662115048)

## 📞 Support

For support, email support@devspark.com or create an issue in the repository.

---

**Note**: This is a project for SE331 - Component-Based Software Development course.