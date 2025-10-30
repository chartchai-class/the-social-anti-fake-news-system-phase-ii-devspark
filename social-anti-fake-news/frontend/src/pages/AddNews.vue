<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-gradient-to-r from-green-600 to-blue-600 text-white py-10 md:py-16 shadow-lg">
      <div class="max-w-2xl mx-auto text-center px-4">
        <h1 class="text-4xl md:text-5xl font-bold mb-3 tracking-tight">Add News</h1>
        <p class="text-lg text-white/90">
          Submit a new article to the community — include title, summary, details, and an image.
        </p>
      </div>

    </header>

    <!-- Main Content -->
    <main class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="bg-white p-8 sm:p-6 md:p-8 lg:p-10 rounded-2xl shadow-xl border border-gray-200">
        <form @submit.prevent="submitNews" class="space-y-6">
          <!-- Title -->
          <div>
            <label for="title" class="block text-sm font-medium text-gray-700 mb-2">Title</label>
            <input
                id="title"
                v-model="title"
                type="text"
                required
                placeholder="Enter your news title"
                class="w-full px-4 py-3 sm:py-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:outline-none transition duration-200"
            />
          </div>

          <!-- Short Detail -->
          <div>
            <label for="shortDetail" class="block text-sm font-medium text-gray-700 mb-2">Short Detail</label>
            <textarea
                id="shortDetail"
                v-model="shortDetail"
                rows="2"
                required
                placeholder="Enter a short summary"
                class="w-full px-4 py-3 sm:py-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:outline-none transition duration-200"
            ></textarea>
          </div>

          <!-- Detail -->
          <div>
            <label for="detail" class="block text-sm font-medium text-gray-700 mb-2">Full Detail</label>
            <textarea
                id="detail"
                v-model="detail"
                rows="5"
                required
                placeholder="Write the full content here..."
                class="w-full px-4 py-3 sm:py-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:outline-none transition duration-200"
            ></textarea>
          </div>

          <!-- Image URL -->
          <div>
            <label for="image" class="block text-sm font-medium text-gray-700 mb-2">Image URL</label>
            <input
                id="image"
                v-model="image"
                type="url"
                placeholder="https://example.com/image.jpg"
                class="w-full px-4 py-3 sm:py-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:outline-none transition duration-200"
            />
            <p class="text-xs text-gray-500 mt-1">Provide a direct image link (JPG, PNG, or GIF)</p>

            <!-- Image Preview -->
            <div v-if="image" class="flex justify-center mt-4">
              <img
                  :src="image"
                  alt="Preview"
                  class="w-full max-w-md h-56 object-cover rounded-xl shadow-lg border border-gray-200"
                  @error="handleImageError"
              />
            </div>
          </div>

          <!-- Submit Button -->
          <div class="pt-4">
            <button
                type="submit"
                class="w-full bg-gradient-to-r from-blue-600 to-green-600 text-white font-semibold py-3 rounded-xl shadow-md hover:opacity-90 active:scale-95 transition duration-200"
            >
              Submit News
            </button>
          </div>
        </form>
      </div>

      <!-- Live Preview -->
      <div
          v-if="title || shortDetail || detail"
          class="bg-gray-50 rounded-xl p-6 border border-gray-200 shadow-sm mt-10"
      >
        <h2 class="text-2xl font-semibold mb-3">{{ title || 'News Title Preview' }}</h2>
        <p class="text-gray-700 mb-4">{{ shortDetail || 'Short summary will appear here.' }}</p>
        <p class="text-gray-600">{{ detail || 'Full details preview will appear here.' }}</p>
      </div>

    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const title = ref('')
const shortDetail = ref('')
const detail = ref('')
const image = ref('')

const handleImageError = () => {
  image.value = '/images/placeholder.jpg'
}

const submitNews = async () => {
  try {
    await addSchema.validate({
      title: title.value,
      shortDetail: shortDetail.value,
      detail: detail.value
    }, {abortEarly: false})
  } catch (err) {
    // collect and show errors (similar to Register)
    alert(err.errors?.join('\n') || err.message)
    return
  }
  const newNews = {
    title: title.value.trim(),
    shortDetail: shortDetail.value.trim(),
    detail: detail.value.trim(),
    image: image.value.trim() || '/images/placeholder.jpg',
    date: new Date().toISOString(),
  }

  const existing = JSON.parse(localStorage.getItem('newsList') || '[]')
  existing.push(newNews)
  localStorage.setItem('newsList', JSON.stringify(existing))

  alert('✅ News added successfully!')
  title.value = ''
  shortDetail.value = ''
  detail.value = ''
  image.value = ''

}
import * as yup from 'yup'
const addSchema = yup.object({
  title: yup.string().required('Title required').min(5,'Min 5 chars'),
  shortDetail: yup.string().required('Short detail required').min(10,'Min 10 chars'),
  detail: yup.string().required('Detail required').min(20,'Min 20 chars')
})


</script>

<style scoped>
/* Optional: smooth fade-in for preview images */
img {
  transition: opacity 0.3s ease-in-out;
}
</style>
