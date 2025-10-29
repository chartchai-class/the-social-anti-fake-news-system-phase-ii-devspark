<template>
  <div
      class="card relative cursor-pointer hover:shadow-xl transition p-4 flex flex-col group bg-white rounded-xl shadow-md border border-gray-100 min-h-[480px]"
      @click="handleClick(news.id)"
  >
    <div class="relative w-full h-48 overflow-hidden rounded-lg mb-4 flex-shrink-0">
      <img
          v-if="news.image"
          :src="news.image"
          :alt="news.title || 'news image'"
          class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
          @error="$event.target.src = '/images/placeholder.jpg'"
      />
      <div v-else class="w-full h-full bg-gray-200 flex items-center justify-center text-gray-500 rounded-lg">
        No Image Available
      </div>
    </div>

    <div class="flex flex-col flex-grow justify-between">
      <div>
        <h2 class="text-xl font-semibold mb-2 text-gray-900 line-clamp-2 hover:text-[#639FFF] transition-colors">{{ news.title }}</h2>

        <p class="text-sm text-gray-600 line-clamp-3 mb-4">{{ news.shortDetail }}</p>
      </div>

      <div class="mt-auto">
        <div class="flex items-center justify-between text-xs text-gray-500 border-t border-gray-100 pt-3 mb-3">
              <span class="flex items-center">
                <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                </svg>
                {{ news.reporter }}
              </span>
          <span class="flex items-center">
                <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                {{ formatDate(news.dateTime) }}
              </span>
        </div>

        <span
            :class="['inline-block px-3 py-1 text-xs rounded-full font-bold uppercase tracking-wider text-center',
              news.status === 'fake' ? 'bg-red-100 text-red-700' :
              news.status === 'notFake' ? 'bg-green-100 text-green-700' :
              'bg-yellow-100 text-yellow-700']"
        >
              {{ news.status === 'fake' ? 'Fake' : news.status === 'notFake' ? 'Real' : 'Undecided' }}
            </span>
      </div>
    </div>
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
  try {
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) {
      return "Invalid Date";
    }
    return date.toLocaleString('en-GB', { dateStyle: 'medium', timeStyle: 'short' });
  } catch (e) {
    return "Invalid Date";
  }
}
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

</style>