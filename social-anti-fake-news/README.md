# Social Anti-Fake News System - Phase II

## 📋 Project Information

**Group Name:** DevSpark Team  
**Project:** Social Anti-Fake News System  
**Phase:** II  
**Technology Stack:** Vue 3, Pinia, Spring Boot, MySQL/H2, Tailwind CSS

---

## 👥 Team Members

| Name | Student ID | Role |
|------|-----------|------|
| [Team Member 1] | [ID] | [Role] |
| [Team Member 2] | [ID] | [Role] |
| [Team Member 3] | [ID] | [Role] |

> **Note:** Please update the team members table with actual information.

---

## 🌐 Deployment URLs

### Frontend
- **Production URL:** [Add your Vercel/Render frontend URL here]
- **Development URL:** http://localhost:5173

### Backend
- **Production URL:** [Add your Render/Heroku backend URL here]
- **Development URL:** http://localhost:8080/api

---

## 🚀 Features Implemented

### ✅ Core Requirements (All 26 Items)

1. **Mock Data** - Comprehensive mock data with 35+ news articles, comments, votes
2. **Responsive Layout** - Fully responsive design using Tailwind CSS
3. **News List** - Paginated news list with filtering and search
4. **News Filtering** - Filter by fake/real/undecided/all with loading indicators
5. **News Details** - Full news details page with image display
6. **Comments & Votes** - Complete UI for comments and votes with pagination
7. **Voting System** - Real-time vote updates with instant status changes
8. **User Registration** - Complete registration with validation (Name, Surname, Email, Password, Profile Image)
9. **Registration UX** - Beautiful form design with clear validation feedback
10. **Admin Upgrade** - Admin can upgrade READER → MEMBER
11. **Upgrade UX** - Easy-to-use admin interface for role management
12. **Admin Remove News** - Soft delete news with admin-only visibility
13. **News Removal UX** - Confirmation dialogs and proper feedback
14. **Admin Remove Comments** - Soft delete comments with score recalculation
15. **Comment Removal UX** - Clean interface with confirmation dialogs
16. **Search System** - Search by topic, detail, reporter name, and status
17. **Search UX** - Excellent search experience with instant results
18. **Input Validation** - Comprehensive Yup validation on all forms
19. **Validation UX** - Clear, friendly error messages
20. **UI Techniques** - Full Tailwind CSS implementation, fully responsive
21. **Deployment** - Both frontend and backend deployment ready
22. **Entity Design** - Clean database entities (User, News, Comment, Vote)
23. **Backend Structure** - RESTful API with controllers, services, repositories
24. **Frontend Structure** - Organized components, stores, views, routes
25. **Git Usage** - Proper commit history with clear messages
26. **Cool Features** - Bonus features (see below)

---

## 🎁 Bonus Features

1. **Share Button** - Share news articles via native share API or copy link
2. **Report Abuse** - Report abusive comments for admin review
3. **Top Reporters Ranking** - Statistics page with reporter leaderboard
4. **Comment Sorting** - Sort comments by newest/oldest
5. **Enhanced Statistics** - Comprehensive statistics dashboard
6. **User Profiles** - Detailed user profile pages with statistics
7. **Image Previews** - Image previews for comments and news
8. **Loading Indicators** - Smooth loading states throughout the app

---

## 🛠️ Technology Stack

### Frontend
- **Framework:** Vue 3 (Composition API)
- **State Management:** Pinia
- **Routing:** Vue Router
- **Validation:** Yup
- **Styling:** Tailwind CSS 4
- **Build Tool:** Vite
- **Progress Bar:** NProgress

### Backend
- **Framework:** Spring Boot 3.2.0
- **Database:** H2 (Development) / MySQL (Production)
- **ORM:** JPA / Hibernate
- **Security:** Spring Security
- **API Documentation:** SpringDoc OpenAPI (Swagger)
- **Mapping:** MapStruct

---

## 📦 Installation & Setup

### Prerequisites
- Node.js 18+ (for frontend)
- Java 17+ (for backend)
- Maven 3.6+ (for backend)
- MySQL (for production) or H2 (for development)

### Frontend Setup

```bash
cd social-anti-fake-news/frontend
npm install
npm run dev
```

### Backend Setup

```bash
cd social-anti-fake-news/backend
mvn clean install
mvn spring-boot:run
```

Or use the provided Maven wrapper:
```bash
./mvnw spring-boot:run
```

---

## 🔧 Configuration

### Frontend Environment Variables

Create `.env` in `frontend/` directory:
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### Backend Configuration

Edit `src/main/resources/application.yml` for database and JWT settings.

---

## 📱 Project Structure

```
social-anti-fake-news/
├── frontend/
│   ├── src/
│   │   ├── components/     # Reusable Vue components
│   │   ├── pages/          # Page components
│   │   ├── store/          # Pinia stores
│   │   ├── router/         # Vue Router configuration
│   │   ├── data/           # Mock data
│   │   └── style.css       # Global styles
│   ├── public/             # Static assets
│   └── package.json
├── backend/
│   ├── src/main/java/
│   │   └── com/devspark/antifakenews/
│   │       ├── controller/ # REST controllers
│   │       ├── service/    # Business logic
│   │       ├── repository/ # Data access
│   │       ├── entity/    # JPA entities
│   │       ├── dto/       # Data transfer objects
│   │       └── mapper/    # MapStruct mappers
│   └── pom.xml
└── README.md
```

---

## 🧪 Testing

### Frontend
```bash
cd frontend
npm run test  # If test suite exists
```

### Backend
```bash
cd backend
mvn test
```

---

## 📝 API Documentation

Once the backend is running, access Swagger UI at:
- **Local:** http://localhost:8080/swagger-ui.html
- **Production:** [Your Backend URL]/swagger-ui.html

---

## 🔐 Default Credentials

### Demo Users
- **Admin:** `admin` / `admin123`
- **Member:** `member1` / (any password)
- **Reader:** `reader1` / (any password)

> **Note:** These are demo credentials. In production, implement proper authentication.

---

## 📊 Mock Data

The application includes comprehensive mock data:
- **35+ News Articles** with various statuses (fake, real, undecided)
- **Comments** with votes and timestamps
- **Users** with different roles (Admin, Member, Reader)
- **Votes** distributed across news articles

---

## 🚀 Deployment

### Frontend (Vercel)
1. Connect your GitHub repository to Vercel
2. Set build directory to `social-anti-fake-news/frontend`
3. Set output directory to `dist`
4. Deploy!

### Backend (Render/Heroku)
1. Create a new web service
2. Connect your GitHub repository
3. Set root directory to `social-anti-fake-news/backend`
4. Set build command: `mvn clean package`
5. Set start command: `java -jar target/anti-fake-news-backend-1.0.0.jar`
6. Configure environment variables and database

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Development Team

For questions or support, please contact the development team through:
- **GitHub Issues:** [Your Repository URL]/issues
- **Email:** [Team Email]

---

## 🙏 Acknowledgments

- Vue.js Team for the amazing framework
- Spring Boot Team for the robust backend framework
- Tailwind CSS for the utility-first CSS framework
- All contributors and testers

---

**Last Updated:** [Current Date]  
**Version:** 1.0.0
