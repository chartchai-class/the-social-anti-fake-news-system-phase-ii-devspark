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
        <div class="hidden md:block">
          <div class="flex items-center space-x-4">
            <RouterLink
                to="/"
                :class="navLinkClass('/')"
            >
              🏠 Home
            </RouterLink>
            <RouterLink
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
            <RouterLink
                to="/register"
                :class="navLinkClass('/register')"
            >
              👨🏿 Account
            </RouterLink>
          </div>
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
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue'
import { RouterLink, useRoute } from 'vue-router'

const route = useRoute()
const mobileMenuOpen = ref(false)

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value
}

const closeMobileMenu = () => {
  mobileMenuOpen.value = false
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