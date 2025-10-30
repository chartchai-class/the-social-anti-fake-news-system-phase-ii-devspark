<template>
  <div v-if="news" class="min-h-screen bg-gray-50">
    <!-- Back Button -->
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 pt-6">
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
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 pb-12">
      <div class="bg-white rounded-2xl shadow-xl overflow-hidden border border-blue-50">
        <!-- Main News Image -->
        <div class="bg-gray-100 flex justify-center items-center px-4 sm:px-6 py-4">
          <img :src="news.image" alt="news image" class="w-full max-w-3xl max-h-96 object-cover rounded-2xl shadow-lg" />
        </div>
        <!-- Big, colored status & votes -->
        <div class="flex flex-col items-center gap-4 mt-6">
          <div class="flex items-center gap-2">
            <span :class="[
                'px-6 py-2 rounded-full uppercase font-extra text-5xl',
                news.status === 'fake' ? 'bg-red-100 text-fake' :
                news.status === 'notFake' ? 'bg-green-100 text-real' :
                'bg-yellow-50 text-undecided'
              ]">
              {{ news.status === 'fake' ? 'FAKE' : news.status === 'notFake' ? 'REAL' : 'UNDECIDED' }}
            </span>
            <span class="text-3xl font-extra flex gap-2 bg-gray-50 px-4 py-2 rounded ml-2">
              <span class="text-fake flex items-center"><span class="text-2xl mr-1">🚫</span>{{ news.votes?.fake || 0 }}</span>
              |
              <span class="text-real flex items-center"><span class="text-2xl mr-1">✅</span>{{ news.votes?.notFake || 0 }}</span>
            </span>
          </div>
        </div>
        <!-- News Title -->
        <h1 class="text-4xl font-extrabold text-center mt-7 mb-4 text-gray-900 leading-tight">
          {{ news.title }}
        </h1>
        <!-- Info bar: reporter, time, comments -->
        <div class="flex flex-wrap items-center justify-center gap-6 mb-6 text-base text-gray-700 border-y border-gray-100 py-4">
          <div class="flex items-center"><svg class="w-5 h-5 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg> <span class="font-medium">Reporter:</span> {{ news.reporter }}</div>
          <div class="flex items-center"><svg class="w-5 h-5 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg> <span class="font-medium">Time Report:</span> {{ formatDate(news.dateTime) }}</div>
          <div class="flex items-center"><svg class="w-5 h-5 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"></path></svg> {{ news.comments.length }} comments</div>
        </div>
        <!-- Community Verdict Bar (optional) -->
        <div class="bg-blue-50 rounded-xl p-6 mb-8 border border-blue-100 mx-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-4">Community Verdict</h3>
          <div class="flex gap-12 justify-center">
            <div class="text-center">
              <div class="text-3xl font-bold text-blue-700 mb-2">{{ news.votes.fake }}</div>
              <div class="text-sm text-gray-600">Votes for Fake</div>
            </div>
            <div class="text-center">
              <div class="text-3xl font-bold text-blue-500 mb-2">{{ news.votes.notFake }}</div>
              <div class="text-sm text-gray-600">Votes for Real</div>
            </div>
          </div>
        </div>
        <!-- News Content -->
        <div class="prose prose-lg max-w-none mx-6 mb-8">
          <p class="text-gray-700 leading-relaxed text-lg">{{ news.fullDetail }}</p>
        </div>
        <!-- Vote Form -->
        <div class="border-t pt-8 mx-6">
          <VoteForm :newsId="news.id" />
        </div>
        <!-- Comments Section -->
        <div class="mt-12 mx-6">
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
        </div> <!-- End Comments -->
      </div> <!-- card -->
    </div>
    <!-- Image Modal stays unchanged -->
    <div v-if="showImageModal" class="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50" @click="closeImageModal">
      <div class="max-w-4xl max-h-full p-4">
        <img :src="modalImageUrl" class="max-w-full max-h-full rounded-lg shadow-2xl" />
      </div>
    </div>
  </div>
  <!-- Not found block stays the same -->
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
