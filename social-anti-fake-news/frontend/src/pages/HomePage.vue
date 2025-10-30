<template>
  <div class="min-h-screen overflow-x-hidden box-border">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8 box-border">
      <!-- Search Bar -->
      <div class="max-w-2xl mx-auto mb-8 px-2 box-border">
        <div class="relative overflow-hidden">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search news articles by title, detail, or reporter..."
            class="w-full box-border px-5 py-3 text-gray-900 rounded-full shadow-lg border border-gray-300 focus:outline-none focus:ring-4 focus:ring-[#8BB8FF] text-base transition-shadow"
          />
          <div class="absolute right-4 top-1/2 transform -translate-y-1/2">
            <svg
              class="w-6 h-6 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
              ></path>
            </svg>
          </div>
        </div>
      </div>
      <!-- Filters -->
      <div class="flex flex-col gap-4 mb-6">
        <div class="flex flex-wrap items-center gap-3 py-1">
          <h3 class="text-lg font-semibold text-gray-700 mr-2 hidden sm:block">Filter By:</h3>
          <button
              v-for="option in filterOptions"
              :key="option.value"
              @click="setFilter(option.value)"
              :class="[
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
            <div
                class="flex items-center gap-2 bg-white rounded-full px-4 py-2 shadow border border-primary/20 text-sm"
            >
              <label class="text-gray-600">Show</label>
              <select
                  v-model="itemsPerPage"
                  @change="handleItemsPerPageChange"
                  class="border-0 bg-transparent text-sm font-medium focus:outline-none"
              >
                <option v-for="n in [5,10,15,20]" :key="n" :value="n">{{ n }}</option>
              </select>
            </div>

            <div class="hidden md:flex flex-wrap gap-3">
              <div
                  class="flex items-center gap-2 bg-white border border-primary/20 rounded-full px-4 py-2 shadow text-sm"
              >
                <span>Fake</span>
                <span class="text-fake font-semibold">{{ fakeNewsCount }}</span>
              </div>
              <div
                  class="flex items-center gap-2 bg-white border border-primary/20 rounded-full px-4 py-2 shadow text-sm"
              >
                <span>Real</span>  <span class="text-real font-semibold">{{ realNewsCount }}</span>
              
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

      <!-- News Grid -->
      <div class="relative news-grid">
        <!-- Loading Overlay -->
        <transition name="fade">
          <div
              v-if="isLoading"
              class="absolute inset-0 bg-white/80 backdrop-blur-sm flex items-center justify-center z-10"
          >
            <svg
                class="w-12 h-12 animate-spin text-blue-600"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
            >
              <circle
                  class="opacity-25"
                  cx="12"
                  cy="12"
                  r="10"
                  stroke="currentColor"
                  stroke-width="4"
              ></circle>
              <path
                  class="opacity-75"
                  fill="currentColor"
                  d="M4 12a8 8 0 018-8v8H4z"
              ></path>
            </svg>
          </div>
        </transition>

        <NewsCard
            v-for="news in paginatedNews"
            :key="news.id"
            :news="news"
            @click="goToDetail(news.id)"
        />
      </div>

      <!-- No News -->
      <div v-if="paginatedNews.length === 0" class="text-center py-12">
        <div
            class="w-24 h-24 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center"
        >
          <span class="text-4xl">📰</span>
        </div>
        <h3 class="text-lg font-medium text-gray-900 mb-2">No news found</h3>
        <p class="text-gray-500">Try adjusting your search or filter criteria</p>
      </div>

      <!-- Pagination -->
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
          <span
              class="px-3 py-1 bg-primary text-white rounded-full text-sm font-medium"
          >{{ currentPage }}</span>
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
const currentPage = ref(1)
const itemsPerPage = ref(6)
const isLoading = ref(false)

const filterOptions = [
  { label: 'All', value: 'all', icon: '📰' },
  { label: 'Fake', value: 'fake', icon: '🚫' },
  { label: 'Real', value: 'notFake', icon: '✅' },
  { label: 'Undecided', value: 'undecided', icon: '❓' }
]

// Statistics
const fakeNewsCount = computed(() => store.newsList.filter(n => n.status === 'fake').length)
const realNewsCount = computed(() => store.newsList.filter(n => n.status === 'notFake').length)

// Search & Filter
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

// Watchers
watch([filter, searchQuery], () => {
  currentPage.value = 1
})

// ----- Functions -----
function setFilter(val) {
  NProgress.start()
  isLoading.value = true
  filter.value = val
  setTimeout(() => {
    isLoading.value = false
    NProgress.done()
  }, 300)
}

function goToDetail(id) {
  isLoading.value = true
  NProgress.start()
  store.setSelectedNews(id)
  router.push(`/news/${id}`).finally(() => {
    NProgress.done()
    isLoading.value = false
  })
}

function prevPage() {
  if (currentPage.value > 1) {
    NProgress.start()
    isLoading.value = true
    currentPage.value--
    setTimeout(() => {
      NProgress.done()
      isLoading.value = false
    }, 300)
  }
}

function nextPage() {
  if (currentPage.value < totalPages.value) {
    NProgress.start()
    isLoading.value = true
    currentPage.value++
    setTimeout(() => {
      NProgress.done()
      isLoading.value = false
    }, 300)
  }
}

function handleItemsPerPageChange() {
  NProgress.start()
  isLoading.value = true
  currentPage.value = 1
  setTimeout(() => {
    NProgress.done()
    isLoading.value = false
  }, 300)
}

function goToAddNews() {
  NProgress.start()
  router.push('/addNews').finally(() => NProgress.done())
}
</script>

<style scoped>
.news-grid {
  display: grid;
  grid-template-columns: repeat(1, minmax(0, 1fr));
  gap: 1.5rem;
}

@media (min-width: 640px) {
  .news-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
@media (min-width: 1024px) {
  .news-grid {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
