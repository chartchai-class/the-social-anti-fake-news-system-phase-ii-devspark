import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './style.css'
import 'nprogress/nprogress.css'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.use(router)

// Initialize auth store on app start
import { useAuthStore } from './store/authStore'
const authStore = useAuthStore()

// Initialize Supabase and auth state
authStore.initialize().then(() => {
  app.mount('#app')
}).catch((error) => {
  console.error('Auth initialization error:', error)
  app.mount('#app')
})
