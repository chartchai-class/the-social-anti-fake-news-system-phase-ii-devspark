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
        <!-- BIG, BOLD TITLE -->
        <h2 class="text-2xl font-extrabold mb-2 text-gray-900 line-clamp-2 hover:text-[#639FFF] transition-colors">{{ news.title }}</h2>
        <p class="text-sm text-gray-600 line-clamp-3 mb-2">{{ news.shortDetail }}</p>
      </div>

      <!-- BOLD, BIG, COLORED STATUS + prominent votes -->
      <div class="flex items-center gap-4 mb-2">
        <span
          :class="[
            'text-4xl font-extrabold uppercase',
            news.status === 'fake' ? 'text-red-600' :
            news.status === 'notFake' ? 'text-green-600' :
            'text-yellow-600'
          ]"
        >
          {{ news.status === 'fake' ? 'FAKE' : news.status === 'notFake' ? 'REAL' : 'UNDECIDED' }}
        </span>
        <span class="text-2xl font-bold flex gap-2">
          <span class="text-red-600 flex items-center"><span class="text-lg mr-1">🚫</span>{{ (news.votes && news.votes.fake) || 0 }}</span>
          | 
          <span class="text-green-600 flex items-center"><span class="text-lg mr-1">✅</span>{{ (news.votes && news.votes.notFake) || 0 }}</span>
        </span>
      </div>
      <!-- Reporter/Time as before, clean -->
      <div class="border-t border-gray-100 pt-2 mt-1">
        <div class="text-xs text-gray-700"><span class="font-semibold">Reporter:</span> {{ news.reporter }}</div>
        <div class="text-xs text-gray-500"><span class="font-semibold">Time Report:</span> {{ formatDate(news.dateTime) }}</div>
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