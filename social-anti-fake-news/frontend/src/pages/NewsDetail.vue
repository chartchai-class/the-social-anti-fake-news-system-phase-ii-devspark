<template>
  <div v-if="news" class="min-h-screen bg-gray-50">
    <!-- Back Button -->
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 pt-6">
      <button
        @click="goBack"
        class="flex items-center text-gray-600 hover:text-blue-600 transition-colors mb-6"
      >
        <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
        </svg>
        Back to News
      </button>
    </div>

    <!-- Main Content -->
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 pb-12">
      <div class="bg-white rounded-2xl shadow-xl overflow-hidden border border-blue-50">
        <!-- Image Header -->
        <div class="relative h-80 overflow-hidden">
          <img :src="news.image" class="w-full h-full object-cover" />
          <div class="absolute inset-0 bg-gradient-to-t from-black/40 to-transparent"></div>

          <!-- Status Badge -->
          <div class="absolute top-6 right-6 flex items-center gap-4">
            <span :class="[
              'px-8 py-4 rounded-full text-4xl font-extrabold shadow-lg',
              news.status === 'fake' ? 'bg-red-100 text-red-700' :
              news.status === 'notFake' ? 'bg-green-100 text-green-700' :
              'bg-yellow-50 text-yellow-700'
            ]">
              {{ news.status === 'fake' ? 'FAKE' : news.status === 'notFake' ? 'REAL' : 'UNDECIDED' }}
            </span>
            <span class="text-2xl text-gray-800 font-bold bg-gray-100 rounded-full px-4 py-2">
              🚫 {{ news.votes?.fake || 0 }} | ✅ {{ news.votes?.notFake || 0 }}
            </span>
          </div>

          <!-- News Title Overlay with BOLD, COLORED STATUS and BOLD VOTES -->
          <div class="absolute bottom-6 left-6 right-6">
            <h1 class="text-5xl font-extrabold text-white mb-3 leading-tight shadow-md">
              {{ news.title }}
            </h1>
            <div class="flex items-center gap-4 mb-2">
              <span
                :class="[
                  'text-4xl font-extrabold uppercase',
                  news.status === 'fake' ? 'text-red-500 bg-white/90 px-4 py-1 rounded' :
                  news.status === 'notFake' ? 'text-green-600 bg-white/90 px-4 py-1 rounded' :
                  'text-yellow-600 bg-white/90 px-4 py-1 rounded'
                ]"
              >
                {{ news.status === 'fake' ? 'FAKE' : news.status === 'notFake' ? 'REAL' : 'UNDECIDED' }}
              </span>
              <span class="text-2xl font-bold flex gap-2 bg-white/90 px-4 py-1 rounded">
                <span class="text-red-600 flex items-center"><span class="text-lg mr-1">🚫</span>{{ news.votes?.fake || 0 }}</span>
                |
                <span class="text-green-600 flex items-center"><span class="text-lg mr-1">✅</span>{{ news.votes?.notFake || 0 }}</span>
              </span>
            </div>
          </div>
        </div>

        <!-- Content -->
        <div class="p-8">
          <!-- Meta Information with clear labels DOWN BELOW, not too bold/large -->
          <div class="flex flex-wrap items-center gap-8 mb-8 mt-10 text-base text-gray-700">
            <div><span class="font-medium">Reporter:</span> {{ news.reporter }}</div>
            <div><span class="font-medium">Time Report:</span> {{ formatDate(news.dateTime) }}</div>
            <div class="text-gray-600">{{ news.comments.length }} comments</div>
          </div>

          <!-- Vote Statistics -->
          <div class="bg-blue-50 rounded-xl p-6 mb-8 border border-blue-100">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Community Verdict</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div class="text-center">
                <div class="text-3xl font-bold text-blue-700 mb-2">{{ news.votes.fake }}</div>
                <div class="text-sm text-gray-600">Votes for Fake</div>
                <div class="w-full bg-gray-200 rounded-full h-2 mt-2">
                  <div
                    class="bg-blue-700 h-2 rounded-full transition-all duration-500"
                    :style="{ width: `${getVotePercentage('fake')}%` }"
                  ></div>
                </div>
              </div>
              <div class="text-center">
                <div class="text-3xl font-bold text-blue-500 mb-2">{{ news.votes.notFake }}</div>
                <div class="text-sm text-gray-600">Votes for Real</div>
                <div class="w-full bg-gray-200 rounded-full h-2 mt-2">
                  <div
                    class="bg-blue-500 h-2 rounded-full transition-all duration-500"
                    :style="{ width: `${getVotePercentage('notFake')}%` }"
                  ></div>
                </div>
              </div>
            </div>
          </div>

          <!-- News Content -->
          <div class="prose prose-lg max-w-none mb-8">
            <p class="text-gray-700 leading-relaxed text-lg">{{ news.fullDetail }}</p>
          </div>

          <!-- Vote Form -->
          <div class="border-t pt-8">
            <VoteForm :newsId="news.id" />
          </div>

          <!-- Comments Section -->
          <div class="mt-12">
            <h2 class="text-2xl font-bold text-gray-900 mb-6">Comments ({{ news.comments.length }})</h2>

            <div v-if="news.comments.length === 0" class="text-center py-8">
              <div class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
                <svg class="w-8 h-8 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"></path>
                </svg>
              </div>
              <p class="text-gray-500">No comments yet. Be the first to share your thoughts!</p>
            </div>

            <div v-else class="space-y-6">
              <div
                v-for="(comment, i) in news.comments"
                :key="i"
                class="bg-gray-50 rounded-xl p-6 hover:bg-gray-100 transition-colors"
              >
                <div class="flex items-start space-x-4">
                  <div class="flex-shrink-0">
                    <div class="w-10 h-10 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center text-white font-semibold">
                      {{ comment.user.charAt(0).toUpperCase() }}
                    </div>
                  </div>
                  <div class="flex-1 min-w-0">
                    <div class="flex items-center space-x-2 mb-2">
                      <h4 class="text-sm font-semibold text-gray-900">{{ comment.user }}</h4>
                      <span :class="[
                        'px-2 py-1 rounded-full text-xs font-medium',
                        comment.vote === 'fake' ? 'bg-red-100 text-red-700' :
                        comment.vote === 'notFake' ? 'bg-green-100 text-green-700' :
                        'bg-gray-100 text-gray-700'
                      ]">
                        {{ comment.vote === 'fake' ? '🚫 Fake' : comment.vote === 'notFake' ? '✅ Real' : '❓ Undecided' }}
                      </span>
                    </div>
                    <p class="text-gray-700 mb-3">{{ comment.text }}</p>
                    <img
                      v-if="comment.imageUrl"
                      :src="comment.imageUrl"
                      class="max-w-xs rounded-lg shadow-md hover:shadow-lg transition-shadow cursor-pointer"
                      @error="comment.imageUrl = '/src/assets/placeholder.png'"
                      @click="openImageModal(comment.imageUrl)"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Image Modal -->
    <div v-if="showImageModal" class="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50" @click="closeImageModal">
      <div class="max-w-4xl max-h-full p-4">
        <img :src="modalImageUrl" class="max-w-full max-h-full rounded-lg shadow-2xl" />
      </div>
    </div>
  </div>

  <div v-else class="min-h-screen flex items-center justify-center">
    <div class="text-center">
      <div class="w-24 h-24 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
        <span class="text-4xl">📰</span>
      </div>
      <h3 class="text-lg font-medium text-gray-900 mb-2">News not found</h3>
      <p class="text-gray-500 mb-6">The news article you're looking for doesn't exist or has been removed.</p>
      <button
        @click="goBack"
        class="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
      >
        Back to News
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useNewsStore } from '../store/newsStore'
import { useRoute, useRouter } from 'vue-router'
import VoteForm from '../components/VoteForm.vue'

const store = useNewsStore()
const route = useRoute()
const router = useRouter()

const showImageModal = ref(false)
const modalImageUrl = ref('')

// Try to get selectedNews from store
let news = store.selectedNews

// If not set, try to load it by ID from route
if (!news && route.params.id) {
  news = store.newsList.find(n => n.id === Number(route.params.id))
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const now = new Date()
  const diffInHours = (now - date) / (1000 * 60 * 60)

  if (diffInHours < 1) return 'Just now'
  if (diffInHours < 24) return `${Math.floor(diffInHours)}h ago`
  if (diffInHours < 48) return 'Yesterday'
  return date.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function getVotePercentage(voteType) {
  if (!news) return 0
  const totalVotes = news.votes.fake + news.votes.notFake
  if (totalVotes === 0) return 0
  return Math.round((news.votes[voteType] / totalVotes) * 100)
}

function goBack() {
  router.push('/')
}

function openImageModal(imageUrl) {
  modalImageUrl.value = imageUrl
  showImageModal.value = true
}

function closeImageModal() {
  showImageModal.value = false
  modalImageUrl.value = ''
}
</script>

<style scoped>
/* Custom line clamp utilities */
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
