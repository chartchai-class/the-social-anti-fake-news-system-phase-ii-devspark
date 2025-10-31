# Social Anti-Fake News System - Phase II

> **Complete anti-fake news verification platform with user authentication, news management, voting, and admin controls**

## 📋 Project Overview

This is a full-stack web application designed to help users verify news articles through community voting and commenting. The system includes role-based access control, comprehensive news management, and detailed statistics.

---

## 🎯 Project Status

✅ **All 27 Requirements Implemented**  
✅ **Bonus Features Added**  
✅ **Deployment Ready**  
✅ **CI/CD Pipeline Configured**

---

## 🌐 Live URLs

### Frontend
- **Production:** [Add your Vercel/Render URL here]
- **Development:** http://localhost:5173

### Backend API
- **Production:** [Add your Render/Heroku URL here]
- **Development:** http://localhost:8080/api
- **API Docs:** http://localhost:8080/swagger-ui.html

---

## 👥 Team Information

| Name | Student ID | Contribution |
|------|-----------|--------------|
| [Member 1] | [ID] | [Role] |
| [Member 2] | [ID] | [Role] |
| [Member 3] | [ID] | [Role] |

**Repository:** [GitHub Classroom Link]  
**GitHub:** https://github.com/[your-org]/[repo-name]

---

## ✨ Key Features

### Core Functionality
- 📰 **News Management** - Create, view, filter, and search news articles
- 🗳️ **Voting System** - Community voting to determine fake/real status
- 💬 **Comments** - Interactive commenting system with image support
- 👤 **User Management** - Registration, profiles, and role-based access
- 🔐 **Authentication** - Secure login and role management
- 🛠️ **Admin Panel** - Complete admin control over users, news, and comments
- 📊 **Statistics** - Comprehensive dashboard with top reporters ranking
- 🔍 **Advanced Search** - Search by topic, detail, reporter, and status

### Bonus Features
- 🔗 **Share Functionality** - Share news via native share API
- 🚨 **Report Abuse** - Report inappropriate comments
- 🏆 **Top Reporters** - Leaderboard showing most active reporters
- 📈 **Enhanced Statistics** - Detailed analytics and insights
- 🎨 **Modern UI/UX** - Beautiful, responsive design with Tailwind CSS

---

## 🛠️ Technology Stack

### Frontend
- Vue 3 (Composition API)
- Pinia (State Management)
- Vue Router
- Tailwind CSS 4
- Yup (Validation)
- Vite (Build Tool)

### Backend
- Spring Boot 3.2.0
- Spring Security
- JPA / Hibernate
- MapStruct (DTO Mapping)
- H2 (Dev) / MySQL (Prod)
- Swagger/OpenAPI

---

## 🚀 Quick Start

### Prerequisites
- Node.js 18+
- Java 17+
- Maven 3.6+

### Installation

**Frontend:**
```bash
cd social-anti-fake-news/frontend
npm install
npm run dev
```

**Backend:**
```bash
cd social-anti-fake-news/backend
mvn spring-boot:run
```

### Demo Users
- **Admin:** `admin` / `admin123`
- **Member:** `member1` / (any)
- **Reader:** `reader1` / (any)

---

## 📁 Project Structure

```
├── social-anti-fake-news/
│   ├── frontend/          # Vue 3 application
│   │   ├── src/
│   │   │   ├── components/
│   │   │   ├── pages/
│   │   │   ├── store/
│   │   │   └── router/
│   │   └── package.json
│   ├── backend/          # Spring Boot API
│   │   ├── src/main/java/
│   │   └── pom.xml
│   └── README.md
└── .github/workflows/    # CI/CD pipeline
```

---

## ✅ Requirements Checklist

- [x] 1. Mock Data (35+ news articles)
- [x] 2. Responsive Layout
- [x] 3. News List with Pagination
- [x] 4. News Filtering (fake/real/undecided)
- [x] 5. News Details Page
- [x] 6. Comments & Votes Section
- [x] 7. Voting System
- [x] 8. User Registration
- [x] 9. Registration UX
- [x] 10. Admin Upgrade (Reader → Member)
- [x] 11. Upgrade UX
- [x] 12. Admin Remove News
- [x] 13. News Removal UX
- [x] 14. Admin Remove Comments
- [x] 15. Comment Removal UX
- [x] 16. Search System
- [x] 17. Search UX
- [x] 18. Input Validation (Yup)
- [x] 19. Validation UX
- [x] 20. UI Techniques (Tailwind)
- [x] 21. Deployment
- [x] 22. Entity Design
- [x] 23. Backend Code Structure
- [x] 24. Frontend Code Structure
- [x] 25. Git Usage
- [x] 26. Cool Features (Bonus)
- [x] 27. CI/CD Pipeline

---

## 🎁 Bonus Features

1. **Share News** - Native share API integration
2. **Report Abuse** - Comment reporting system
3. **Top Reporters** - Leaderboard ranking
4. **Comment Sorting** - Newest/Oldest filter
5. **Enhanced Statistics** - Detailed analytics
6. **User Profiles** - Comprehensive profile pages
7. **Image Previews** - Preview images in comments
8. **Loading States** - Smooth UX transitions

---

## 📚 Documentation

- **API Documentation:** Available at `/swagger-ui.html` when backend is running
- **Frontend Docs:** See `social-anti-fake-news/frontend/README.md`
- **Backend Docs:** See `social-anti-fake-news/backend/README.md`

---

## 🧪 Testing

```bash
# Frontend tests
cd frontend && npm test

# Backend tests
cd backend && mvn test
```

---

## 🚀 Deployment

### Frontend (Vercel)
- Configured in `frontend/vercel.json`
- Automatic deployments via GitHub Actions

### Backend (Render/Heroku)
- Maven build configuration
- Environment variables required for production

See deployment guides in respective README files.

---

## 📝 License

This project is part of a university course assignment.

---

## 🙏 Acknowledgments

Built with Vue.js, Spring Boot, and Tailwind CSS.

---

**Version:** 1.0.0  
**Last Updated:** 2025-01-XX
