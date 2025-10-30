// src/router/index.js
import { createRouter, createWebHashHistory } from 'vue-router'

// Import your pages
import HomePage from '../pages/HomePage.vue'
import NewsDetail from '../pages/NewsDetail.vue'
import AddNews from '../pages/AddNews.vue'
import Statistics from '../pages/Statistics.vue'
import Register from '../pages/Register.vue'
import AdminPanel from '../pages/AdminPanel.vue'

const routes = [
  { path: '/', name: 'Home', component: HomePage },
  { path: '/news/:id', name: 'Detail', component: NewsDetail },
  { path: '/add', name: 'Add', component: () => import('../pages/AddNews.vue') },
  { path: '/addNews', name: 'AddNews', component: AddNews },
  { path: '/statistics', name: 'Statistics', component: Statistics },
  { path: '/register', component: Register },
  { path: '/admin', component: AdminPanel }
]
const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
