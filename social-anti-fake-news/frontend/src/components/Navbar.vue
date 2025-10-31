<template>
  <nav class="bg-white shadow-lg border-b border-gray-200 sticky top-0 z-50">
    <div class="max-w-5xl mx-auto px-3 sm:px-4 lg:px-6">
      <div class="flex justify-between items-center h-14">
        <!-- Logo - smaller on mobile -->
        <RouterLink to="/" class="flex items-center space-x-2 group transition-opacity duration-200 hover:opacity-90 focus:outline-none focus-visible:ring-2 focus-visible:ring-[#639FFF] rounded-lg">
          <div class="flex-shrink-0">
            <div class="w-8 h-8 bg-gradient-to-r from-[#639FFF] to-primary-dark rounded-lg flex items-center justify-center shadow-md">
              <span class="text-white font-bold text-sm">AF</span>
            </div>
          </div>
          <div>
            <h1 class="text-lg sm:text-xl font-extrabold text-primary-gradient tracking-wide transition-opacity duration-200">
              Anti-Fake News
            </h1>
            <p class="text-xs text-gray-500 hidden sm:block">Truth Matters</p>
          </div>
        </RouterLink>

        <!-- Desktop Navigation - reduced spacing -->
        <div class="hidden md:flex items-center space-x-4">
            <RouterLink
                to="/"
                :class="navLinkClass('/')"
            >
              🏠 Home
            </RouterLink>
            <RouterLink
                v-if="auth.isMember"
                to="/addNews"
                :class="navLinkClass('/addNews')"
            >
              ➕ Add News
            </RouterLink>
            <RouterLink
                to="/statistics"
                :class="navLinkClass('/statistics')"
            >
              📊 Statistics
            </RouterLink>
            <template v-if="!auth.isLoggedIn">
              <RouterLink to="/login" :class="navLinkClass('/login')">Login</RouterLink>
              <RouterLink to="/register" :class="navLinkClass('/register')">Sign up</RouterLink>
            </template>
            <div v-else class="relative">
              <button @click="profileOpen = !profileOpen" class="flex items-center space-x-2 px-2 py-1.5 rounded-lg hover:bg-primary/10">
                <img :src="auth.user.imageUrl || '/images/avatar-default.png'" class="w-7 h-7 rounded-full object-cover" />
                <span class="text-sm font-medium text-gray-700">{{ auth.user.name }}</span>
                <svg class="w-4 h-4 text-gray-500" viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M5.23 7.21a.75.75 0 011.06.02L10 10.94l3.71-3.71a.75.75 0 111.06 1.06l-4.24 4.24a.75.75 0 01-1.06 0L5.25 8.29a.75.75 0 01-.02-1.08z" clip-rule="evenodd"/></svg>
              </button>
              <div v-if="profileOpen" class="absolute right-0 mt-2 w-44 bg-white border border-gray-200 rounded-lg shadow-lg py-1 z-50">
                <RouterLink :to="`/user/${auth.user.id}`" class="block px-3 py-2 text-sm hover:bg-gray-50">Profile</RouterLink>
                <RouterLink :to="{ path: '/', query: { me: '1' } }" class="block px-3 py-2 text-sm hover:bg-gray-50">My News</RouterLink>
                <button @click="logout" class="block w-full text-left px-3 py-2 text-sm hover:bg-gray-50">Logout</button>
              </div>
            </div>
            <RouterLink
                v-if="auth.isAdmin"
                to="/admin"
                :class="navLinkClass('/admin')"
            >
              🛠 Admin
            </RouterLink>
          </div>

        <!-- Mobile Menu Button -->
        <div class="md:hidden">
          <button
              @click="toggleMobileMenu"
              class="inline-flex items-center justify-center p-2 rounded-md bg-primary text-white focus:outline-none focus-visible:ring-2 focus-visible:ring-inset focus-visible:ring-primary transition-colors shadow-md hover:bg-primary-dark"
              :aria-expanded="mobileMenuOpen ? 'true' : 'false'"
              aria-controls="mobile-menu"
          >
            <span class="text-sm font-bold text-white">{{ mobileMenuOpen ? 'Close' : 'Menu' }}</span>
            <svg class="h-5 w-5 ml-1 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path v-if="!mobileMenuOpen" stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 12h16M4 18h16" />
              <path v-else stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>

      <div v-if="mobileMenuOpen" id="mobile-menu" class="md:hidden">
        <div class="pt-2 pb-3 space-y-1 bg-gray-50 border-t border-gray-200 shadow-inner">
          <RouterLink
              to="/"
              @click="closeMobileMenu"
              :class="mobileNavLinkClass('/')"
          >
            🏠 Home
          </RouterLink>
          <RouterLink
              v-if="auth.isMember"
              to="/addNews"
              @click="closeMobileMenu"
              :class="mobileNavLinkClass('/addNews')"
          >
            ➕ Add News
          </RouterLink>
          <RouterLink
              to="/statistics"
              @click="closeMobileMenu"
              :class="mobileNavLinkClass('/statistics')"
          >
            📊 Statistics
          </RouterLink>
          <RouterLink v-if="!auth.isLoggedIn" to="/login" @click="closeMobileMenu" :class="mobileNavLinkClass('/login')">Login</RouterLink>
          <RouterLink v-if="!auth.isLoggedIn" to="/register" @click="closeMobileMenu" :class="mobileNavLinkClass('/register')">Sign up</RouterLink>
          <RouterLink
              v-if="auth.isAdmin"
              to="/admin"
              @click="closeMobileMenu"
              :class="mobileNavLinkClass('/admin')"
          >
            🛠 Admin
          </RouterLink>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { useAuthStore } from '../store/authStore'

const route = useRoute()
const mobileMenuOpen = ref(false)
const profileOpen = ref(false)
const auth = useAuthStore()

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value
}

const closeMobileMenu = () => {
  mobileMenuOpen.value = false
}

function logout(){
  auth.logout()
  profileOpen.value = false
}

// Utility function for desktop link classes - smaller padding
const navLinkClass = (path) => [
  'px-2 py-1.5 rounded-lg text-sm font-medium transition-all duration-200 hover:scale-[1.03]',
  route.path === path
      ? 'bg-primary/20 text-primary-dark font-bold'
      : 'text-gray-600 hover:text-primary hover:bg-primary/10'
]

// Utility function for mobile link classes
const mobileNavLinkClass = (path) => [
  'block px-3 py-2 rounded-md text-base font-medium transition-colors',
  route.path === path
      ? 'bg-primary/20 text-primary-dark font-bold'
      : 'text-gray-700 hover:text-primary hover:bg-primary/10'
]
</script>

<style scoped>
.text-primary-gradient {
  background: linear-gradient(90deg, #1e293b 40%, #4A86E6 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-fill-color: transparent;
}
</style>