<template>
  <nav class="bg-white shadow-lg border-b border-gray-200 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <!-- Logo and Brand -->
        <div class="flex items-center space-x-3">
          <div class="flex-shrink-0">
            <div class="w-10 h-10 bg-gradient-to-r from-[#639FFF] to-primary-dark rounded-lg flex items-center justify-center">
              <span class="text-white font-bold text-lg">📰</span>
            </div>
          </div>
          <div>
            <h1 class="text-xl font-bold text-gray-900">Anti-Fake News</h1>
            <p class="text-xs text-gray-500">Truth Matters</p>
          </div>
        </div>

        <!-- Navigation Links -->
        <div class="hidden md:block">
          <div class="ml-10 flex items-baseline space-x-8">
            <RouterLink 
              to="/" 
              :class="[
                'px-3 py-2 rounded-md text-sm font-medium transition-all duration-200',
                $route.path === '/' 
                  ? 'bg-primary/10 text-primary-dark' 
                  : 'text-gray-600 hover:text-primary hover:bg-primary/10'
              ]"
            >
              🏠 Home
            </RouterLink>
            <RouterLink 
              to="/addNews" 
              :class="[
                'px-3 py-2 rounded-md text-sm font-medium transition-all duration-200',
                $route.path === '/addNews' 
                  ? 'bg-primary/10 text-primary-dark' 
                  : 'text-gray-600 hover:text-primary hover:bg-primary/10'
              ]"
            >
              ➕ Add News
            </RouterLink>
            <RouterLink 
              to="/statistics" 
              :class="[
                'px-3 py-2 rounded-md text-sm font-medium transition-all duration-200',
                $route.path === '/statistics' 
                  ? 'bg-primary/10 text-primary-dark' 
                  : 'text-gray-600 hover:text-primary hover:bg-primary/10'
              ]"
            >
              📊 Statistics
            </RouterLink>
          </div>
        </div>

        <!-- Mobile menu button -->
        <div class="md:hidden">
          <button 
            @click="toggleMobileMenu"
            class="inline-flex items-center justify-center p-2 rounded-md text-gray-600 hover:text-primary hover:bg-primary/10 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-[#639FFF]"
          >
            <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path v-if="!mobileMenuOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
              <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>

      <!-- Mobile menu -->
      <div v-if="mobileMenuOpen" class="md:hidden">
        <div class="px-2 pt-2 pb-3 space-y-1 sm:px-3 bg-white border-t border-gray-200">
          <RouterLink 
            to="/" 
            @click="closeMobileMenu"
            class="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:text-primary hover:bg-primary/10"
          >
            🏠 Home
          </RouterLink>
          <RouterLink 
            to="/addNews" 
            @click="closeMobileMenu"
            class="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:text-primary hover:bg-primary/10"
          >
            ➕ Add News
          </RouterLink>
          <RouterLink 
            to="/statistics" 
            @click="closeMobileMenu"
            class="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:text-primary hover:bg-primary/10"
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
</script>
