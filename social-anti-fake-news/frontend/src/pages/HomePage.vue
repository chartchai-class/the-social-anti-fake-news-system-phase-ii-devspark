<template>
  <div class="min-h-screen">
    <!-- Hero Section -->
    <div class="bg-gradient-to-r from-[#639FFF] to-primary-dark text-white py-8">
      <div class="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h1 class="text-3xl md:text-4xl font-bold mb-2">📰 Anti-Fake News</h1>
        <p class="text-base md:text-lg mb-6 opacity-90">Fighting misinformation, one truth at a time</p>

        <!-- Search Bar -->
        <div class="max-w-2xl mx-auto">
          <div class="relative">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="Search news articles..."
              class="w-full px-4 py-3 text-gray-900 rounded-full shadow-lg focus:outline-none focus:ring-4 focus:ring-[#8BB8FF] text-base"
            />
            <div class="absolute right-4 top-1/2 transform -translate-y-1/2">
              <svg class="w-6 h-6 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Controls -->
      <div class="flex flex-col gap-4 mb-6">
        <div class="flex gap-3 overflow-x-auto no-scrollbar py-1">
          <button
            v-for="option in filterOptions"
            :key="option.value"
            @click="setFilter(option.value)"
            :class="[
              'px-4 py-2 rounded-full text-sm font-semibold transition-all duration-200 shadow-md hover:shadow-lg',
              filter === option.value
                ? 'bg-primary text-white shadow-blue-200'
                : 'bg-white text-gray-700 hover:bg-primary/10 border border-primary/20'
            ]"
          >
            {{ option.icon }} {{ option.label }}
          </button>
        </div>
          <div class="flex items-center gap-4">
          <div class="flex items-center gap-2 bg-white rounded-full px-4 py-2 shadow border border-primary/20 text-sm">
            <label class="text-gray-600">Show</label>
            <select v-model="itemsPerPage" class="border-0 bg-transparent text-sm font-medium focus:outline-none">
              <option v-for="n in [5,10,15,20]" :key="n" :value="n">{{n}}</option>
            </select>
          </div>
          <button
            @click="goToAddNews"
            class="px-5 py-2 bg-primary text-white font-semibold rounded-full hover:bg-primary-dark shadow-md hover:shadow-lg transition-all duration-200"
          >
            ➕ Add News
          </button>
        </div>
      </div>

      <!-- Statistics Pills -->
      <div class="flex flex-wrap gap-3 mb-6">
        <div class="flex items-center gap-2 bg-white border border-primary/20 rounded-full px-4 py-2 shadow text-sm">
          <span>🚫 Fake</span>
          <span class="text-primary font-semibold">{{ fakeNewsCount }}</span>
        </div>
        <div class="flex items-center gap-2 bg-white border border-primary/20 rounded-full px-4 py-2 shadow text-sm">
          <span>✅ Real</span>
          <span class="text-primary font-semibold">{{ realNewsCount }}</span>
        </div>
        <div class="flex items-center gap-2 bg-white border border-primary/20 rounded-full px-4 py-2 shadow text-sm">
          <span>❓ Undecided</span>
          <span class="text-primary-dark font-semibold">{{ undecidedNewsCount }}</span>
        </div>
      </div>

      <!-- News Grid -->
      <div class="grid grid-cols-2 gap-6 md:gap-8">
        <div
          v-for="news in paginatedNews"
          :key="news.id"
          @click="goToDetail(news.id)"
          class="bg-white rounded-xl shadow-lg hover:shadow-xl cursor-pointer transition-all duration-300 transform hover:-translate-y-1 overflow-hidden group border border-primary/10"
        >
          <!-- Image -->
          <div class="relative aspect-16-9 overflow-hidden">
            <img :src="news.image" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300" alt="news thumbnail" />
            <div class="absolute top-4 right-4">
              <span :class="[
                'px-3 py-1 rounded-full text-xs font-bold shadow-lg',
                news.status === 'fake' ? 'bg-red-600 text-white' :
                news.status === 'notFake' ? 'bg-green-600 text-white' :
                'bg-primary-dark text-white'
              ]">
                {{ news.status === 'fake' ? 'Fake' : news.status === 'notFake' ? 'Real' : 'Undecided' }}
              </span>
            </div>
          </div>

          <!-- Content -->
          <div class="p-6">
            <h3 class="text-2xl font-semibold text-gray-900 mb-2 line-clamp-2 group-hover:text-primary transition-colors">
              {{ news.title }}
            </h3>
            <p class="text-gray-600 text-base mb-5 line-clamp-3 leading-relaxed">{{ news.shortDetail }}</p>

            <!-- Meta Info -->
            <div class="flex items-center justify-between text-xs text-gray-500 mb-4">
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

            <!-- Vote Totals -->
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-4">
                <div class="flex items-center text-blue-600">
                  <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" clip-rule="evenodd"></path>
                  </svg>
                  <span class="text-sm font-semibold">{{ news.votes.fake }}</span>
                </div>
                <div class="flex items-center text-blue-500">
                  <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" clip-rule="evenodd"></path>
                  </svg>
                  <span class="text-sm font-semibold">{{ news.votes.notFake }}</span>
                </div>
              </div>
              <div class="text-primary text-sm font-medium group-hover:text-primary-dark">
                Read More →
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="paginatedNews.length === 0" class="text-center py-12">
        <div class="w-24 h-24 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
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
