<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 flex items-center py-12 px-4">
    <div class="max-w-md mx-auto w-full">
      <div class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
        <!-- Header -->
        <div class="bg-gradient-to-r from-blue-600 to-purple-600 text-white py-8 px-8 text-center">
          <h1 class="text-3xl font-bold mb-2">Sign In</h1>
          <p class="text-blue-100">Welcome back to our community</p>
        </div>

        <!-- Form -->
        <div class="p-8">
          <form @submit.prevent="login" class="space-y-6">
            <!-- Email -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Email <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="form.email" 
                type="email"
                placeholder="your.email@example.com"
                :class="[
                  'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
                  errors.email ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                ]"
              />
              <p v-if="errors.email" class="mt-1 text-sm text-red-600">{{ errors.email }}</p>
            </div>

            <!-- Password -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Password <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="form.password" 
                type="password"
                placeholder="Enter your password"
                :class="[
                  'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
                  errors.password ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                ]"
              />
              <p v-if="errors.password" class="mt-1 text-sm text-red-600">{{ errors.password }}</p>
            </div>

            <!-- Error Message -->
            <div v-if="errors.general" class="bg-red-50 border-l-4 border-red-500 p-4 rounded">
              <p class="text-sm text-red-700">{{ errors.general }}</p>
            </div>

            <!-- Submit Button -->
            <button 
              type="submit"
              :disabled="isSubmitting"
              class="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white font-bold py-4 rounded-lg shadow-lg hover:shadow-xl hover:from-blue-700 hover:to-purple-700 focus:outline-none focus:ring-4 focus:ring-blue-300 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 transform hover:-translate-y-0.5"
            >
              <span v-if="!isSubmitting" class="flex items-center justify-center">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"></path>
                </svg>
                Sign In
              </span>
              <span v-else class="flex items-center justify-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Signing In...
              </span>
            </button>
          </form>

          <!-- Quick Test Login -->
          <!-- Test buttons removed -->

          <!-- Register Link -->
          <div class="mt-6 text-center border-t pt-6">
            <p class="text-gray-600">
              Don't have an account? 
              <router-link to="/register" class="text-blue-600 font-semibold hover:text-blue-800 hover:underline transition-colors">
                Create one here
              </router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/authStore'
import * as yup from 'yup'

const router = useRouter()
const auth = useAuthStore()
const isSubmitting = ref(false)
const errors = reactive({})

const form = reactive({
  email: '',
  password: ''
})

// Yup validation schema
const loginSchema = yup.object({
  email: yup.string()
    .required('Email is required')
    .email('Please enter a valid email address'),
  
  password: yup.string()
    .required('Password is required')
    .min(6, 'Password must be at least 6 characters')
})

async function login() {
  // Clear previous errors
  Object.keys(errors).forEach(key => delete errors[key])
  
  isSubmitting.value = true
  
  try {
    // Validate form for normal Supabase login (email must contain @)
    await loginSchema.validate(form, { abortEarly: false })

    // Login with Supabase
    const result = await auth.login(form.email.trim(), form.password)
    
    if (result.success) {
      // Success - redirect to home
      router.push('/')
    }
    
  } catch (err) {
    if (err.inner) {
      err.inner.forEach(error => {
        errors[error.path] = error.message
      })
    } else if (err.message) {
      // Supabase errors
      if (err.message.includes('Invalid login credentials')) {
        errors.general = 'Invalid email or password. Please try again.'
      } else if (err.message.includes('Email not confirmed')) {
        errors.general = 'Please verify your email before signing in. Check your inbox.'
      } else {
        errors.general = err.message || 'Login failed. Please try again.'
      }
    } else {
      errors.general = 'Validation failed. Please check your input.'
    }
  } finally {
    isSubmitting.value = false
  }
}

// dev test logic removed
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
