import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import './style.css'
import 'nprogress/nprogress.css'


const app = createApp(App)
app.use(router)
app.use(createPinia())
app.mount('#app')

import { useAuthStore } from './store/authStore'
const auth = useAuthStore()
auth.addMockUsersIfEmpty()