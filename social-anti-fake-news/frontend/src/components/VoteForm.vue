<template>
  <div class="bg-white rounded-xl shadow-lg p-6">
    <h3 class="text-xl font-bold text-gray-900 mb-6">Share Your Opinion</h3>
    
    <form @submit.prevent="submitVote" class="space-y-6">
      <!-- Vote Selection -->
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-4">What do you think about this news?</label>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <label class="relative cursor-pointer">
            <input 
              type="radio" 
              value="fake" 
              v-model="voteType" 
              class="sr-only peer"
            />
            <div class="p-4 border-2 border-gray-200 rounded-xl hover:border-red-300 peer-checked:border-red-500 peer-checked:bg-red-50 transition-all duration-200">
              <div class="flex items-center space-x-3">
                <div class="w-6 h-6 border-2 border-gray-300 rounded-full peer-checked:border-red-500 peer-checked:bg-red-500 flex items-center justify-center">
                  <div class="w-2 h-2 bg-white rounded-full opacity-0 peer-checked:opacity-100"></div>
                </div>
                <div>
                  <div class="text-lg font-semibold text-gray-900">🚫 Fake News</div>
                  <div class="text-sm text-gray-500">This appears to be false or misleading</div>
                </div>
              </div>
            </div>
          </label>
          
          <label class="relative cursor-pointer">
            <input 
              type="radio" 
              value="notFake" 
              v-model="voteType" 
              class="sr-only peer"
            />
            <div class="p-4 border-2 border-gray-200 rounded-xl hover:border-green-300 peer-checked:border-green-500 peer-checked:bg-green-50 transition-all duration-200">
              <div class="flex items-center space-x-3">
                <div class="w-6 h-6 border-2 border-gray-300 rounded-full peer-checked:border-green-500 peer-checked:bg-green-500 flex items-center justify-center">
                  <div class="w-2 h-2 bg-white rounded-full opacity-0 peer-checked:opacity-100"></div>
                </div>
                <div>
                  <div class="text-lg font-semibold text-gray-900">✅ Real News</div>
                  <div class="text-sm text-gray-500">This appears to be accurate and trustworthy</div>
                </div>
              </div>
            </div>
          </label>
        </div>
        <div v-if="voteError" class="mt-2 text-red-600 text-sm flex items-center">
          <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
          </svg>
          Please select your opinion
        </div>
      </div>

      <!-- User Name -->
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-2">Your Name (Optional)</label>
        <input 
          v-model="userName" 
          type="text"
          placeholder="Enter your name or stay anonymous" 
          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
        />
      </div>

      <!-- Comment -->
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-2">
          Your Comment <span class="text-red-500">*</span>
        </label>
        <textarea 
          v-model="commentText" 
          placeholder="Share your thoughts about this news article..."
          rows="4"
          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors resize-none"
        ></textarea>
        <div v-if="commentError" class="mt-2 text-red-600 text-sm flex items-center">
          <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
          </svg>
          Please enter a comment
        </div>
      </div>

      <!-- Image URL -->
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-2">Supporting Image (Optional)</label>
        <input 
          v-model="imageUrl" 
          type="url"
          placeholder="https://example.com/image.jpg" 
          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
        />
        <p class="mt-1 text-xs text-gray-500">Add an image URL to support your comment</p>
      </div>

      <!-- Submit Button -->
      <button 
        type="submit" 
        :disabled="isSubmitting"
        class="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white font-semibold py-3 px-6 rounded-lg hover:from-blue-700 hover:to-purple-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 transform hover:-translate-y-0.5 hover:shadow-lg"
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
import { ref } from 'vue'
import { useNewsStore } from '../store/newsStore'

const store = useNewsStore()
const voteType = ref('')
const userName = ref('')
const commentText = ref('')
const imageUrl = ref('')

const voteError = ref(false)
const commentError = ref(false)
const isSubmitting = ref(false)

const props = defineProps({ newsId: Number })

async function submitVote() {
  // Reset errors
  voteError.value = false
  commentError.value = false

  // Validate form
  if (!voteType.value) {
    voteError.value = true
    return
  }

  if (!commentText.value.trim()) {
    commentError.value = true
    return
  }

  // Set submitting state
  isSubmitting.value = true

  try {
    // Simulate API call delay
    await new Promise(resolve => setTimeout(resolve, 1000))

    // Submit vote and comment
    store.vote(props.newsId, voteType.value)
    store.addComment(props.newsId, {
      user: userName.value.trim() || 'Anonymous', 
      text: commentText.value.trim(), 
      imageUrl: imageUrl.value.trim(),
      vote: voteType.value
    })

    // Reset form
    voteType.value = ''
    userName.value = ''
    commentText.value = ''
    imageUrl.value = ''
    voteError.value = false
    commentError.value = false

    // Show success message (you could add a toast notification here)
    console.log('Vote submitted successfully!')
  } catch (error) {
    console.error('Error submitting vote:', error)
  } finally {
    isSubmitting.value = false
  }
}
</script>
