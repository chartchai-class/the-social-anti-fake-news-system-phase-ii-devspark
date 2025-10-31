<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 flex items-center py-12 px-4">
    <div class="max-w-2xl w-full mx-auto">
      <div class="bg-white rounded-2xl shadow-2xl border border-gray-100 overflow-hidden">
        <!-- Header -->
        <div class="bg-gradient-to-r from-blue-600 to-purple-600 text-white py-8 px-8 text-center">
          <h2 class="text-3xl font-bold mb-2">Create Your Account</h2>
          <p class="text-blue-100">Join our community to verify news and share your thoughts</p>
        </div>

        <!-- Form -->
        <div class="p-8">
          <form @submit.prevent="submit" class="space-y-6">
            <!-- Name -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Name <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="form.name" 
                type="text"
                placeholder="Enter your full name"
                :class="[
                  'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
                  errors.name ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                ]"
              />
              <p v-if="errors.name" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
                </svg>
                {{ errors.name }}
              </p>
            </div>

            <!-- Surname -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Surname <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="form.surname" 
                type="text"
                placeholder="Enter your surname"
                :class="[
                  'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
                  errors.surname ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                ]"
              />
              <p v-if="errors.surname" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
                </svg>
                {{ errors.surname }}
              </p>
            </div>

            <!-- Username -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Username <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="form.username" 
                type="text"
                placeholder="Choose a unique username"
                :class="[
                  'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
                  errors.username ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                ]"
              />
              <p v-if="errors.username" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
                </svg>
                {{ errors.username }}
              </p>
            </div>

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
              <p v-if="errors.email" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
                </svg>
                {{ errors.email }}
              </p>
            </div>

            <!-- Password -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Password <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="form.password" 
                type="password"
                placeholder="Minimum 8 characters"
                :class="[
                  'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
                  errors.password ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                ]"
              />
              <p v-if="errors.password" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
                </svg>
                {{ errors.password }}
              </p>
              <p v-else class="mt-1 text-xs text-gray-500">Must be at least 8 characters with letters and numbers</p>
            </div>

            <!-- Confirm Password -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Confirm Password <span class="text-red-500">*</span>
              </label>
              <input 
                v-model="form.confirmPassword" 
                type="password"
                placeholder="Re-enter your password"
                :class="[
                  'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
                  errors.confirmPassword ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                ]"
              />
              <p v-if="errors.confirmPassword" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
                </svg>
                {{ errors.confirmPassword }}
              </p>
            </div>

            <!-- Profile Image -->
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">
                Profile Image URL (Optional)
              </label>
              <input 
                v-model="form.imageUrl" 
                type="url"
                placeholder="https://example.com/profile.jpg or /images/avatar.jpg"
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all"
              />
              <p v-if="errors.imageUrl" class="mt-1 text-sm text-red-600">{{ errors.imageUrl }}</p>
              <p v-else class="mt-1 text-xs text-gray-500">Enter a valid image URL</p>
              
              <!-- Image Preview -->
              <div v-if="form.imageUrl && !errors.imageUrl" class="mt-4 flex justify-center">
                <img 
                  :src="form.imageUrl" 
                  alt="Profile preview" 
                  class="w-24 h-24 rounded-full object-cover border-4 border-blue-200 shadow-lg"
                  @error="handleImageError"
                />
              </div>
            </div>

            <!-- Submit Button -->
            <div class="pt-4">
              <button 
                type="submit"
                :disabled="isSubmitting"
                class="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white font-bold py-4 rounded-lg shadow-lg hover:shadow-xl hover:from-blue-700 hover:to-purple-700 focus:outline-none focus:ring-4 focus:ring-blue-300 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 transform hover:-translate-y-0.5"
              >
                <span v-if="!isSubmitting" class="flex items-center justify-center">
                  <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"></path>
                  </svg>
                  Create Account
                </span>
                <span v-else class="flex items-center justify-center">
                  <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Creating Account...
                </span>
              </button>
            </div>
          </form>

          <!-- Login Link -->
          <div class="mt-6 text-center border-t pt-6">
            <p class="text-gray-600">
              Already have an account? 
              <router-link to="/login" class="text-blue-600 font-semibold hover:text-blue-800 hover:underline transition-colors">
                Sign in here
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
import { supabase } from '../services/supabase'

const router = useRouter()
const auth = useAuthStore()
const isSubmitting = ref(false)
const errors = reactive({})

const form = reactive({
  name: '',
  surname: '',
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  imageUrl: ''
})

// Yup validation schema
const registerSchema = yup.object({
  name: yup.string()
    .required('Name is required')
    .min(2, 'Name must be at least 2 characters')
    .max(50, 'Name must be less than 50 characters')
    .matches(/^[a-zA-Z\s]+$/, 'Name can only contain letters and spaces'),
  
  surname: yup.string()
    .required('Surname is required')
    .min(2, 'Surname must be at least 2 characters')
    .max(50, 'Surname must be less than 50 characters')
    .matches(/^[a-zA-Z\s]+$/, 'Surname can only contain letters and spaces'),
  
  username: yup.string()
    .required('Username is required')
    .min(3, 'Username must be at least 3 characters')
    .max(30, 'Username must be less than 30 characters')
    .matches(/^[a-zA-Z0-9_]+$/, 'Username can only contain letters, numbers, and underscores'),
  
  email: yup.string()
    .required('Email is required')
    .email('Please enter a valid email address')
    .max(100, 'Email must be less than 100 characters'),
  
  password: yup.string()
    .required('Password is required')
    .min(8, 'Password must be at least 8 characters')
    .matches(/^(?=.*[a-zA-Z])(?=.*\d)/, 'Password must contain at least one letter and one number'),
  
  confirmPassword: yup.string()
    .required('Please confirm your password')
    .oneOf([yup.ref('password')], 'Passwords must match'),
  
  imageUrl: yup.string()
    .url('Please enter a valid URL')
    .notRequired()
})

async function submit() {
  // Clear previous errors
  Object.keys(errors).forEach(key => delete errors[key])
  
  isSubmitting.value = true
  
  try {
    // Validate form
    await registerSchema.validate(form, { abortEarly: false })
    
    // Check if username already exists in Supabase
    const { data: existingUsername } = await supabase
      .from('profiles')
      .select('id')
      .eq('username', form.username.toLowerCase().trim())
      .single()
    
    if (existingUsername) {
      errors.username = 'Username already taken'
      isSubmitting.value = false
      return
    }
    
    // Check if email already exists
    const { data: existingEmail } = await supabase
      .from('profiles')
      .select('id')
      .eq('email', form.email.toLowerCase().trim())
      .single()
    
    if (existingEmail) {
      errors.email = 'Email already registered'
      isSubmitting.value = false
      return
    }
    
    // Register user with Supabase
    const result = await auth.register({
      username: form.username.trim(),
      email: form.email.trim(),
      password: form.password,
      name: form.name.trim(),
      surname: form.surname.trim(),
      imageUrl: form.imageUrl.trim() || ''
    })
    
    if (result.success) {
      // Success - show notification and redirect
      alert('✅ Account created successfully! Please check your email to verify your account.')
      router.push('/')
    }
    
  } catch (err) {
    if (err.inner) {
      err.inner.forEach(error => {
        errors[error.path] = error.message
      })
    } else if (err.message) {
      // Supabase errors
      if (err.message.includes('email')) {
        errors.email = 'Email is already registered'
      } else if (err.message.includes('password')) {
        errors.password = 'Password does not meet requirements'
      } else {
        errors.general = err.message || 'Registration failed. Please try again.'
      }
    } else {
      errors.general = 'Validation failed. Please check your input.'
    }
  } finally {
    isSubmitting.value = false
  }
}

function handleImageError(event) {
  event.target.src = '/images/avatar-default.png'
}
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
