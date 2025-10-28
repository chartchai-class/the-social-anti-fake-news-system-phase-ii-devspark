<template>
  <div 
    class="card relative cursor-pointer hover:shadow-xl transition p-4 flex flex-col"
    @click="handleClick(news.id)"
  >
    <!-- Loader overlay -->
    <div v-if="loadingId === news.id" class="absolute inset-0 bg-black/50 flex items-center justify-center rounded">
      <div class="loader border-t-4 border-yellow-400 border-b-4 border-transparent w-8 h-8 rounded-full animate-spin"></div>
    </div>

    <img 
      :src="news.imageUrl" 
      alt="news image" 
      class="w-full h-44 md:h-52 object-cover rounded mb-3"
    />
    <h2 class="text-lg md:text-xl font-semibold mb-1 text-gray-900 hover:text-[#639FFF] transition-colors">{{ news.title }}</h2>
    <p class="text-sm md:text-base text-gray-600 flex-1">{{ news.shortDetail }}</p>
    <div class="mt-2 text-xs md:text-sm text-gray-500 flex justify-between">
      <span>By {{ news.reporter }}</span>
      <span>{{ formatDate(news.dateTime) }}</span>
    </div>
    <span 
      :class="['mt-3 inline-block px-2.5 py-1.5 text-xs rounded-full font-semibold', 
      news.status === 'Fake' ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700']"
    >
      {{ news.status }}
    </span>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useNewsStore } from '../store/newsStore'

defineProps({ news: Object })

const store = useNewsStore()
const router = useRouter()
const loadingId = ref(null)

function handleClick(newsId) {
  loadingId.value = newsId
  // simulate loading before navigation
  setTimeout(() => {
    store.setSelectedNews(newsId)
    router.push(`/news/${newsId}`)
    loadingId.value = null
  }, 800) // 0.8s loading
}

function formatDate(dateStr) {
  return new Date(dateStr).toLocaleString('en-GB', { dateStyle: 'medium', timeStyle: 'short' })
}
</script>

<style scoped>
.loader {
  border-radius: 50%;
  border-style: solid;
  border-width: 4px;
  border-top-color: #ffb703;
  border-right-color: transparent;
  border-bottom-color: #ffb703;
  border-left-color: transparent;
  width: 2rem;
  height: 2rem;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
