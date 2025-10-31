// src/router/index.js
import { createRouter, createWebHashHistory } from 'vue-router'

// Import your pages
import HomePage from '../pages/HomePage.vue'
import NewsDetail from '../pages/NewsDetail.vue'
import AddNews from '../pages/AddNews.vue'
import Statistics from '../pages/Statistics.vue'
import Register from '../pages/Register.vue'
import AdminPanel from '../pages/AdminPanel.vue'
import Login from '../pages/Login.vue'
import UserDetail from '../pages/UserDetail.vue'

const routes = [
  { path: '/', name: 'Home', component: HomePage },
  { path: '/news/:id', name: 'Detail', component: NewsDetail },
  { path: '/add', name: 'Add', component: () => import('../pages/AddNews.vue') },
  { path: '/addNews', name: 'AddNews', component: AddNews },
  { path: '/statistics', name: 'Statistics', component: Statistics },
  { path: '/register', component: Register },
  { path: '/login', component: Login },
  { path: '/user/:id', component: UserDetail },
  { path: '/admin', component: AdminPanel }
]
const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router

// Simple route guards based on localStorage (works with our mock auth store)
router.beforeEach((to, from, next) => {
  const authRaw = localStorage.getItem('afn_auth_v1')
  const user = authRaw ? JSON.parse(authRaw) : null
  const role = user?.role || 'READER'

  if (to.path.startsWith('/admin')) {
    if (role !== 'ADMIN') return next('/')
  }
  if (to.path === '/addNews') {
    if (!(role === 'ADMIN' || role === 'MEMBER')) return next('/register')
  }
  next()
})
