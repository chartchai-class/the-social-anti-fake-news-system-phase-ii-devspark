<template>
  <div class="min-h-screen bg-gradient-to-br from-green-50 via-white to-blue-50">
    <!-- Header -->
    <header class="bg-gradient-to-r from-green-600 to-blue-600 text-white py-12 shadow-lg">
      <div class="max-w-4xl mx-auto text-center px-4">
        <h1 class="text-4xl md:text-5xl font-bold mb-3 tracking-tight">Submit News Article</h1>
        <p class="text-lg text-white/90">
          Share important news with our community. All submissions will be reviewed.
        </p>
      </div>
    </header>

    <!-- Main Content -->
    <main class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="bg-white p-8 sm:p-10 rounded-2xl shadow-xl border border-gray-200">
        <form @submit.prevent="submitNews" class="space-y-6">
          <!-- Title -->
          <div>
            <label for="title" class="block text-sm font-semibold text-gray-700 mb-2">
              Title <span class="text-red-500">*</span>
            </label>
            <input
              id="title"
              v-model="form.title"
              type="text"
              placeholder="Enter a clear and descriptive title"
              :class="[
                'w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 transition-all',
                errors.title ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
              ]"
            />
            <p v-if="errors.title" class="mt-1 text-sm text-red-600 flex items-center">
              <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
              </svg>
              {{ errors.title }}
            </p>
            <p v-else class="mt-1 text-xs text-gray-500">Make it clear and descriptive (10-100 characters)</p>
          </div>

          <!-- Short Detail -->
          <div>
            <label for="shortDetail" class="block text-sm font-semibold text-gray-700 mb-2">
              Short Summary <span class="text-red-500">*</span>
            </label>
            <textarea
              id="shortDetail"
              v-model="form.shortDetail"
              rows="3"
              placeholder="Provide a brief summary that will appear in the news list..."
              :class="[
                'w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 transition-all resize-none',
                errors.shortDetail ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
              ]"
            ></textarea>
            <p v-if="errors.shortDetail" class="mt-1 text-sm text-red-600 flex items-center">
              <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
              </svg>
              {{ errors.shortDetail }}
            </p>
            <p v-else class="mt-1 text-xs text-gray-500">{{ form.shortDetail.length }}/500 characters</p>
          </div>

          <!-- Full Detail -->
          <div>
            <label for="detail" class="block text-sm font-semibold text-gray-700 mb-2">
              Full Article Content <span class="text-red-500">*</span>
            </label>
            <textarea
              id="detail"
              v-model="form.detail"
              rows="8"
              placeholder="Write the complete article with all details, facts, and context..."
              :class="[
                'w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 transition-all resize-none',
                errors.detail ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
              ]"
            ></textarea>
            <p v-if="errors.detail" class="mt-1 text-sm text-red-600 flex items-center">
              <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"></path>
              </svg>
              {{ errors.detail }}
            </p>
            <p v-else class="mt-1 text-xs text-gray-500">{{ form.detail.length }}/5000 characters</p>
          </div>

          <!-- Image URL -->
          <div>
            <label for="image" class="block text-sm font-semibold text-gray-700 mb-2">
              Image URL (Optional)
            </label>
            <input
              id="image"
              v-model="form.image"
              type="url"
              placeholder="https://example.com/image.jpg"
              :class="[
                'w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 transition-all',
                errors.image ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
              ]"
            />
            <p v-if="errors.image" class="mt-1 text-sm text-red-600">{{ errors.image }}</p>
            <p v-else class="mt-1 text-xs text-gray-500">Provide a direct image link (JPG, PNG, or GIF). If empty, a default image will be used.</p>

            <!-- Image Preview -->
            <div v-if="form.image && !errors.image" class="mt-4 flex justify-center">
              <img
                :src="form.image"
                alt="Preview"
                class="w-full max-w-md h-64 object-cover rounded-xl shadow-lg border-2 border-gray-200"
                @error="handleImageError"
              />
            </div>
          </div>

          <!-- Or Upload Image File -->
          <div>
            <label class="block text-sm font-semibold text-gray-700 mb-2">
              Or upload image file (stored in Supabase)
            </label>
            <input
              type="file"
              accept="image/*"
              @change="onFileChange"
              class="w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 transition-all border-gray-300 focus:ring-blue-500 bg-white"
            />
            <p v-if="uploadError" class="mt-1 text-sm text-red-600">{{ uploadError }}</p>
            <p v-if="selectedFileName" class="mt-1 text-xs text-gray-500">Selected: {{ selectedFileName }}</p>
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
          <div class="pt-4 flex gap-4">
            <button
              type="submit"
              :disabled="isSubmitting"
              class="flex-1 bg-gradient-to-r from-blue-600 to-green-600 text-white font-bold py-4 rounded-xl shadow-lg hover:shadow-xl hover:from-blue-700 hover:to-green-700 focus:outline-none focus:ring-4 focus:ring-blue-300 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 transform hover:-translate-y-0.5"
            >
              <span v-if="!isSubmitting" class="flex items-center justify-center">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"></path>
                </svg>
                Submit News Article
              </span>
              <span v-else class="flex items-center justify-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Submitting...
              </span>
            </button>
            <router-link
              to="/"
              class="px-6 py-4 bg-gray-100 text-gray-700 font-semibold rounded-xl hover:bg-gray-200 focus:outline-none focus:ring-4 focus:ring-gray-300 transition-all"
            >
              Cancel
            </router-link>
          </div>
        </form>

        <!-- Live Preview -->
        <div
          v-if="form.title || form.shortDetail || form.detail"
          class="bg-gradient-to-br from-gray-50 to-blue-50 rounded-xl p-6 border-2 border-gray-200 shadow-sm mt-10"
        >
          <h3 class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
            <svg class="w-5 h-5 mr-2 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
            </svg>
            Live Preview
          </h3>
          <div class="space-y-3">
            <h4 class="text-xl font-bold text-gray-900">{{ form.title || 'News Title Preview' }}</h4>
            <p class="text-gray-700 font-medium">{{ form.shortDetail || 'Short summary will appear here...' }}</p>
            <p class="text-gray-600 text-sm leading-relaxed">{{ form.detail || 'Full article content preview will appear here...' }}</p>
            <div v-if="form.image" class="mt-4">
              <img :src="form.image" alt="Preview" class="w-full max-w-xs rounded-lg shadow-md" @error="handleImageError" />
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useNewsStore } from '../store/newsStore'
import { useAuthStore } from '../store/authStore'
import * as yup from 'yup'
import { newsService, storageService } from '../services/supabase'

const router = useRouter()
const newsStore = useNewsStore()
const auth = useAuthStore()

const isSubmitting = ref(false)
const errors = reactive({})
const selectedFile = ref(null)
const selectedFileName = ref('')
const uploadError = ref('')

const form = reactive({
  title: '',
  shortDetail: '',
  detail: '',
  image: ''
})

// Yup validation schema
const newsSchema = yup.object({
  title: yup.string()
    .required('Title is required')
    .min(10, 'Title must be at least 10 characters')
    .max(100, 'Title must be less than 100 characters')
    .trim(),
  
  shortDetail: yup.string()
    .required('Short summary is required')
    .min(20, 'Short summary must be at least 20 characters')
    .max(500, 'Short summary must be less than 500 characters')
    .trim(),
  
  detail: yup.string()
    .required('Full article content is required')
    .min(50, 'Full article must be at least 50 characters')
    .max(5000, 'Full article must be less than 5000 characters')
    .trim(),
  
  image: yup.string()
    .url('Please enter a valid image URL')
    .notRequired()
})

async function submitNews() {
  // Clear previous errors
  Object.keys(errors).forEach(key => delete errors[key])
  uploadError.value = ''
  
  // Check permissions
  if (!auth.isMember && !auth.isAdmin) {
    errors.general = 'Only MEMBER and ADMIN users can submit news. Please request an upgrade from an administrator.'
    return
  }
  
  isSubmitting.value = true
  
  try {
    // Validate form
    await newsSchema.validate(form, { abortEarly: false })
    
    // If a file was selected, upload to Supabase Storage and use its public URL
    let imageUrl = form.image.trim()
    if (selectedFile.value) {
      try {
        const { publicUrl } = await storageService.uploadImage(selectedFile.value, { bucket: 'images', folder: 'news' })
        imageUrl = publicUrl
      } catch (e) {
        uploadError.value = e.message || 'Image upload failed'
        throw e
      }
    }

    // Persist to Supabase
    const created = await newsService.createNews({
      title: form.title.trim(),
      shortDetail: form.shortDetail.trim(),
      detail: form.detail.trim(),
      image: imageUrl || '/images/placeholder.jpg',
      reporterId: auth.user?.id
    })

    // Optimistically sync to local store for immediate UI update
    newsStore.addNews({
      id: created.id,
      title: created.title,
      shortDetail: created.short_detail,
      detail: created.full_detail,
      image: created.image_url || '/images/placeholder.jpg',
      reporterName: auth.user?.name || 'Anonymous',
      reporterId: created.reporter_id,
      createdAt: created.created_at,
      status: (created.status || 'UNDECIDED').toLowerCase()
    })
    
    alert('✅ News article submitted successfully!')
    
    // Reset form
    form.title = ''
    form.shortDetail = ''
    form.detail = ''
    form.image = ''
    selectedFile.value = null
    selectedFileName.value = ''
    
    router.push('/')
    
  } catch (err) {
    if (err.inner) {
      err.inner.forEach(error => {
        errors[error.path] = error.message
      })
    } else {
      errors.general = err.message || 'Validation failed. Please check your input.'
    }
  } finally {
    isSubmitting.value = false
  }
}

function handleImageError(event) {
  event.target.src = '/images/placeholder.jpg'
}

function onFileChange(e) {
  const file = e.target.files?.[0]
  if (file) {
    selectedFile.value = file
    selectedFileName.value = file.name
  } else {
    selectedFile.value = null
    selectedFileName.value = ''
  }
}
</script>

<style scoped>

</style>
