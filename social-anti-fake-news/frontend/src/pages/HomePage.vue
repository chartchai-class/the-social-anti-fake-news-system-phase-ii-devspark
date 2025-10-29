<template>
  <div class="min-h-screen">

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">

      <div class="max-w-3xl mx-auto mb-8">
        <div class="relative">
          <input
              v-model="searchQuery"
              type="text"
              placeholder="Search news articles by title, detail, or reporter..."
              class="w-full px-5 py-3 text-gray-900 rounded-full shadow-lg border border-gray-300 focus:outline-none focus:ring-4 focus:ring-[#8BB8FF] text-base transition-shadow"
          />
          <div class="absolute right-4 top-1/2 transform -translate-y-1/2">
            <svg class="w-6 h-6 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="flex flex-col gap-4 mb-6">

        <div class="flex flex-wrap items-center gap-3 py-1">
          <h3 class="text-lg font-semibold text-gray-700 mr-2 hidden sm:block">Filter By:</h3>
          <button
              v-for="option in filterOptions"
              :key="option.value"
              @click="setFilter(option.value)"
              :class="[
              // flex-shrink-0 is crucial here to guarantee button width
              'flex-shrink-0 px-4 py-2 rounded-full text-sm font-semibold transition-all duration-200 shadow-md hover:shadow-lg',
              filter === option.value
                ? 'bg-primary text-white shadow-blue-200'
                : 'bg-white text-gray-700 hover:bg-primary/10 border border-primary/20'
            ]"
          >
            {{ option.icon }} {{ option.label }}
          </button>
        </div>

        <div class="flex items-center justify-between gap-4 border-t border-gray-200 pt-4">
          <div class="flex items-center space-x-4">
            <div class="flex items-center gap-2 bg-white rounded-full px-4 py-2 shadow border border-primary/20 text-sm">
              <label class="text-gray-600">Show</label>
              <select v-model="itemsPerPage" class="border-0 bg-transparent text-sm font-medium focus:outline-none">
                <option v-for="n in [5,10,15,20]" :key="n" :value="n">{{n}}</option>
              </select>
            </div>

            <div class="hidden md:flex flex-wrap gap-3">
              <div class="flex items-center gap-2 bg-white border border-primary/20 rounded-full px-4 py-2 shadow text-sm">
                <span>🚫 Fake</span>
                <span class="text-red-600 font-semibold">{{ fakeNewsCount }}</span>
              </div>
              <div class="flex items-center gap-2 bg-white border border-primary/20 rounded-full px-4 py-2 shadow text-sm">
                <span>✅ Real</span>
                <span class="text-green-600 font-semibold">{{ realNewsCount }}</span>
              </div>
            </div>
          </div>

          <button
              @click="goToAddNews"
              class="px-5 py-2 bg-gradient-to-r from-green-600 to-blue-600 text-white font-semibold rounded-full shadow-md hover:opacity-90 transition-all duration-200"
          >
            ➕ Add News
          </button>
        </div>
      </div>

      <div class="news-grid">
        <NewsCard
            v-for="news in paginatedNews"
            :key="news.id"
            :news="news"
        />
      </div>

      <div v-if="paginatedNews.length === 0" class="text-center py-12">
        <div class="w-24 h-24 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
          <span class="text-4xl">📰</span>
        </div>
        <h3 class="text-lg font-medium text-gray-900 mb-2">No news found</h3>
        <p class="text-gray-500">Try adjusting your search or filter criteria</p>
      </div>

      <div class="flex justify-center items-center mt-10 gap-3">
        <button
            @click="prevPage"
            :disabled="currentPage === 1"
            class="px-3 py-2 rounded-full border border-primary/30 bg-white text-gray-700 hover:bg-primary/10 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm"
        >
          ← Previous
        </button>

        <div class="flex items-center space-x-2">
          <span class="text-sm text-gray-500">Page</span>
          <span class="px-3 py-1 bg-primary text-white rounded-full text-sm font-medium">{{ currentPage }}</span>
          <span class="text-sm text-gray-500">of {{ totalPages }}</span>
        </div>

        <button
            @click="nextPage"
            :disabled="currentPage === totalPages"
            class="px-3 py-2 rounded-full border border-primary/30 bg-white text-gray-700 hover:bg-primary/10 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm"
        >
          Next →
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useNewsStore } from '../store/newsStore'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

import NewsCard from '../components/NewsCard.vue'

// Configure NProgress
NProgress.configure({ showSpinner: false, speed: 500, minimum: 0.1 })

const store = useNewsStore()
const router = useRouter()

const filter = ref('all')
const searchQuery = ref('')
const filterOptions = [
  { label: 'All', value: 'all', icon: '📰' },
  { label: 'Fake', value: 'fake', icon: '🚫' },
  { label: 'Real', value: 'notFake', icon: '✅' },
  { label: 'Undecided', value: 'undecided', icon: '❓' }
]

const currentPage = ref(1)
const itemsPerPage = ref(6)

// Statistics
const fakeNewsCount = computed(() => store.newsList.filter(n => n.status === 'fake').length)
const realNewsCount = computed(() => store.newsList.filter(n => n.status === 'notFake').length)
const undecidedNewsCount = computed(() => store.newsList.filter(n => n.status === 'undecided').length)

// Search and filter logic
const searchedNews = computed(() => {
  if (!searchQuery.value.trim()) return store.newsList

  const query = searchQuery.value.toLowerCase()
  return store.newsList.filter(news =>
      news.title.toLowerCase().includes(query) ||
      news.shortDetail.toLowerCase().includes(query) ||
      news.fullDetail.toLowerCase().includes(query) ||
      news.reporter.toLowerCase().includes(query)
  )
})

const filteredNews = computed(() => {
  if (filter.value === 'all') return searchedNews.value
  return searchedNews.value.filter(n => n.status === filter.value)
})

const totalPages = computed(() => Math.ceil(filteredNews.value.length / itemsPerPage.value))
const paginatedNews = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value
  return filteredNews.value.slice(start, start + itemsPerPage.value)
})

// Watch for changes that should reset pagination
watch([filter, searchQuery], () => {
  currentPage.value = 1
})

// ----- Functions -----

function setFilter(val) {
  NProgress.start()
  filter.value = val
  setTimeout(() => NProgress.done(), 200)
}

function goToDetail(id) {
  NProgress.start()
  store.setSelectedNews(id)
  router.push(`/news/${id}`).finally(() => NProgress.done())
}

function prevPage() {
  if (currentPage.value > 1) {
    NProgress.start()
    currentPage.value--
    setTimeout(() => NProgress.done(), 200)
  }
}

function nextPage() {
  if (currentPage.value < totalPages.value) {
    NProgress.start()
    currentPage.value++
    setTimeout(() => NProgress.done(), 200)
  }
}

function goToAddNews() {
  NProgress.start()
  router.push('/addNews').finally(() => NProgress.done())
}

function formatDate(dateStr) {
  const date = new Date(dateStr)
  const now = new Date()
  const diffInHours = (now - date) / (1000 * 60 * 60)

  if (diffInHours < 1) return 'Just now'
  if (diffInHours < 24) return `${Math.floor(diffInHours)}h ago`
  if (diffInHours < 48) return 'Yesterday'
  return date.toLocaleDateString()
}
</script>

<style scoped>

.news-grid {
  display: grid;
  grid-template-columns: repeat(1, minmax(0, 1fr));
  gap: 1.5rem; /* gap-6 */
}

@media (min-width: 640px) { /* 'sm' breakpoint for tablets/small laptops */
  .news-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
@media (min-width: 1024px) { /* 'lg' breakpoint */
  .news-grid {
    grid-template-columns: repeat(3, minmax(0, 1fr)); /* Optional 3rd column for large screens */
  }
}

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