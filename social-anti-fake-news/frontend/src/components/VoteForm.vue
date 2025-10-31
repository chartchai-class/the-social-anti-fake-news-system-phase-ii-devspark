<template>
  <div class="bg-white rounded-xl shadow-lg p-6 border-2 border-blue-100">
    <h3 class="text-xl font-bold text-gray-900 mb-6 flex items-center">
      <svg class="w-6 h-6 mr-2 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"></path>
      </svg>
      Share Your Opinion
    </h3>
    
    <form @submit.prevent="submitVote" class="space-y-6">
      <!-- Vote Selection -->
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-4">What do you think about this news?</label>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <label class="relative cursor-pointer">
            <input 
              type="radio" 
              value="fake" 
              v-model="form.voteType" 
              class="sr-only peer"
            />
            <div class="p-5 border-2 border-gray-200 rounded-xl hover:border-red-300 peer-checked:border-red-500 peer-checked:bg-red-50 transition-all duration-200 shadow-sm hover:shadow-md">
              <div class="flex items-center space-x-3">
                <div class="w-6 h-6 border-2 border-gray-300 rounded-full peer-checked:border-red-500 peer-checked:bg-red-500 flex items-center justify-center transition-all">
                  <div class="w-2 h-2 bg-white rounded-full opacity-0 peer-checked:opacity-100 transition-opacity"></div>
                </div>
                <div>
                  <div class="text-lg font-bold text-gray-900">🚫 Fake News</div>
                  <div class="text-sm text-gray-500">This appears to be false or misleading</div>
                </div>
              </div>
            </div>
          </label>
          
          <label class="relative cursor-pointer">
            <input 
              type="radio" 
              value="notFake" 
              v-model="form.voteType" 
              class="sr-only peer"
            />
            <div class="p-5 border-2 border-gray-200 rounded-xl hover:border-green-300 peer-checked:border-green-500 peer-checked:bg-green-50 transition-all duration-200 shadow-sm hover:shadow-md">
              <div class="flex items-center space-x-3">
                <div class="w-6 h-6 border-2 border-gray-300 rounded-full peer-checked:border-green-500 peer-checked:bg-green-500 flex items-center justify-center transition-all">
                  <div class="w-2 h-2 bg-white rounded-full opacity-0 peer-checked:opacity-100 transition-opacity"></div>
                </div>
                <div>
                  <div class="text-lg font-bold text-gray-900">✅ Real News</div>
                  <div class="text-sm text-gray-500">This appears to be accurate and trustworthy</div>
                </div>
              </div>
            </div>
          </label>
        </div>
        <p v-if="errors.voteType" class="mt-2 text-red-600 text-sm flex items-center">
          <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
          </svg>
          {{ errors.voteType }}
        </p>
      </div>

      <!-- User Name -->
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-2">Your Name (Optional)</label>
        <input 
          v-model="form.userName" 
          type="text"
          placeholder="Enter your name or stay anonymous" 
          :class="[
            'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
            errors.userName ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
          ]"
        />
        <p v-if="errors.userName" class="mt-1 text-sm text-red-600">{{ errors.userName }}</p>
      </div>

      <!-- Comment -->
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-2">
          Your Comment <span class="text-red-500">*</span>
        </label>
        <textarea 
          v-model="form.commentText" 
          placeholder="Share your thoughts about this news article..."
          rows="4"
          :class="[
            'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all resize-none',
            errors.commentText ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
          ]"
        ></textarea>
        <p v-if="errors.commentText" class="mt-1 text-sm text-red-600 flex items-center">
          <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
          </svg>
          {{ errors.commentText }}
        </p>
        <p v-else class="mt-1 text-xs text-gray-500">{{ form.commentText.length }}/1000 characters</p>
      </div>

      <!-- Image URL -->
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-2">Supporting Image (Optional)</label>
        <input 
          v-model="form.imageUrl" 
          type="url"
          placeholder="https://example.com/image.jpg" 
          :class="[
            'w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 transition-all',
            errors.imageUrl ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
          ]"
        />
        <p v-if="errors.imageUrl" class="mt-1 text-sm text-red-600">{{ errors.imageUrl }}</p>
        <p v-else class="mt-1 text-xs text-gray-500">Add an image URL to support your comment</p>
        
        <!-- Image Preview -->
        <div v-if="form.imageUrl && !errors.imageUrl" class="mt-4 flex justify-center">
          <img 
            :src="form.imageUrl" 
            alt="Preview"
            class="max-w-xs max-h-48 rounded-lg shadow-md border-2 border-gray-200"
            @error="handleImageError"
          />
        </div>
      </div>

      <!-- Error Summary -->
      <div v-if="errors.general" class="bg-red-50 border-l-4 border-red-500 p-4 rounded">
        <div class="flex">
          <svg class="w-5 h-5 text-red-500 mr-2" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path>
          </svg>
          <p class="text-sm text-red-700">{{ errors.general }}</p>
        </div>
      </div>

      <!-- Submit Button -->
      <button 
        type="submit" 
        :disabled="isSubmitting"
        class="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white font-bold py-4 px-6 rounded-lg hover:from-blue-700 hover:to-purple-700 focus:outline-none focus:ring-4 focus:ring-blue-300 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 transform hover:-translate-y-0.5 hover:shadow-lg"
      >
        <span v-if="!isSubmitting" class="flex items-center justify-center">
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"></path>
          </svg>
          Submit Your Opinion
        </span>
        <span v-else class="flex items-center justify-center">
          <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
          </svg>
          Submitting...
        </span>
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useNewsStore } from '../store/newsStore'
import { useAuthStore } from '../store/authStore'
import * as yup from 'yup'

const newsStore = useNewsStore()
const auth = useAuthStore()

const props = defineProps({ newsId: Number })

const isSubmitting = ref(false)
const errors = reactive({})

const form = reactive({
  voteType: '',
  userName: '',
  commentText: '',
  imageUrl: ''
})

// Yup validation schema
const voteSchema = yup.object({
  voteType: yup.string()
    .required('Please select your opinion (Fake or Real)')
    .oneOf(['fake', 'notFake'], 'Please select a valid option'),
  
  userName: yup.string()
    .max(50, 'Name must be less than 50 characters')
    .notRequired(),
  
  commentText: yup.string()
    .required('Comment is required')
    .min(10, 'Comment must be at least 10 characters')
    .max(1000, 'Comment must be less than 1000 characters')
    .trim(),
  
  imageUrl: yup.string()
    .url('Please enter a valid image URL')
    .notRequired()
})

async function submitVote() {
  // Clear previous errors
  Object.keys(errors).forEach(key => delete errors[key])
  
  isSubmitting.value = true
  
  try {
    // Validate form
    await voteSchema.validate(form, { abortEarly: false })
    
    // Submit vote
    newsStore.vote(props.newsId, form.voteType)
    
    // Submit comment
    newsStore.addComment(props.newsId, form.userName.trim() || auth.user?.name || 'Anonymous', form.commentText.trim(), form.imageUrl.trim())
    
    // Success - reset form
    form.voteType = ''
    form.userName = ''
    form.commentText = ''
    form.imageUrl = ''
    
    // Show success (you could use a toast notification here)
    alert('✅ Your opinion has been submitted successfully!')
    
  } catch (err) {
    if (err.inner) {
      err.inner.forEach(error => {
        errors[error.path] = error.message
      })
    } else {
      errors.general = err.message || 'Validation failed'
    }
  } finally {
    isSubmitting.value = false
  }
}

function handleImageError(event) {
  event.target.style.display = 'none'
}
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
