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
          <img 
            :src="news.image || '/images/placeholder.jpg'" 
            :alt="news.title" 
            class="w-full max-w-3xl max-h-96 object-cover rounded-2xl shadow-lg" 
            @error="handleImageError"
          />
        </div>
        
        <!-- Status & Votes -->
        <div class="flex flex-col items-center gap-4 mt-6 px-6">
          <div class="flex items-center gap-4 flex-wrap justify-center">
            <span :class="[
              'px-6 py-3 rounded-full uppercase font-bold text-lg',
              news.status === 'fake' ? 'bg-red-100 text-red-700' :
              news.status === 'notFake' ? 'bg-green-100 text-green-700' :
              'bg-yellow-100 text-yellow-700'
            ]">
              {{ news.status === 'fake' ? '🚫 FAKE' : news.status === 'notFake' ? '✅ REAL' : '❓ UNDECIDED' }}
            </span>
            <div class="text-xl font-bold flex items-center gap-5 bg-gray-50 px-6 py-3 rounded-xl">
              <span class="text-red-600 flex items-center gap-2">
                <span class="text-2xl">🚫</span>{{ news.votes?.fake || 0 }}
              </span>
              <span class="text-gray-300">|</span>
              <span class="text-green-600 flex items-center gap-2">
                <span class="text-2xl">✅</span>{{ news.votes?.notFake || 0 }}
              </span>
            </div>
          </div>
        </div>
        
        <!-- News Title -->
        <h1 class="text-4xl font-extrabold text-center mt-7 mb-4 text-gray-900 leading-tight px-6">
          {{ news.title }}
        </h1>
        
        <!-- Info bar: reporter, time, comments, actions -->
        <div class="flex flex-wrap items-center justify-center gap-6 mb-6 text-base text-gray-700 border-y border-gray-100 py-4 px-6">
          <div class="flex items-center">
            <svg class="w-5 h-5 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
            </svg>
            <span class="font-medium">Reporter:</span> 
            <router-link :to="`/user/${news.reporterId || 0}`" class="ml-1 text-blue-600 hover:underline">
              {{ news.reporter }}
            </router-link>
          </div>
          <div class="flex items-center">
            <svg class="w-5 h-5 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <span class="font-medium">Time Report:</span> {{ formatDate(news.dateTime || news.createdAt) }}
          </div>
          <div class="flex items-center">
            <svg class="w-5 h-5 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"></path>
            </svg>
            {{ visibleComments.length }} comments
          </div>
          
          <!-- Share Button -->
          <button
            @click="shareNews"
            class="flex items-center gap-2 px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-lg transition-colors"
          >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z"></path>
            </svg>
            Share
          </button>
        </div>
        
        <!-- Community Verdict Bar -->
        <div class="bg-blue-50 rounded-xl p-6 mb-8 border border-blue-100 mx-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-4">Community Verdict</h3>
          <div class="flex gap-12 justify-center">
            <div class="text-center">
              <div class="text-3xl font-bold text-red-600 mb-2">{{ news.votes?.fake || 0 }}</div>
              <div class="text-sm text-gray-600">Votes for Fake</div>
            </div>
            <div class="text-center">
              <div class="text-3xl font-bold text-green-600 mb-2">{{ news.votes?.notFake || 0 }}</div>
              <div class="text-sm text-gray-600">Votes for Real</div>
            </div>
          </div>
        </div>
        
        <!-- News Content -->
        <div class="prose prose-lg max-w-none mx-6 mb-8">
          <p class="text-gray-700 leading-relaxed text-lg">{{ news.fullDetail || news.detail }}</p>
        </div>
        
        <!-- Vote Form -->
        <div class="border-t pt-8 mx-6 mb-8">
          <VoteForm :newsId="news.id" />
        </div>
        
        <!-- Comments Section -->
        <div class="mt-12 mx-6 pb-8">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-2xl font-bold text-gray-900">Comments ({{ visibleComments.length }})</h2>
            <div class="flex gap-2">
              <select 
                v-model="commentSort" 
                class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <option value="newest">Newest First</option>
                <option value="oldest">Oldest First</option>
              </select>
            </div>
          </div>
          
          <div v-if="visibleComments.length === 0" class="text-center py-12">
            <div class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
              <svg class="w-8 h-8 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"></path>
              </svg>
            </div>
            <p class="text-gray-500">No comments yet. Be the first to share your thoughts!</p>
          </div>
          
          <div v-else class="space-y-6">
            <div
              v-for="comment in sortedComments"
              :key="comment.id"
              class="bg-gray-50 rounded-xl p-6 hover:bg-gray-100 transition-colors border border-gray-200"
            >
              <div class="flex items-start space-x-4">
                <div class="flex-shrink-0">
                  <div class="w-12 h-12 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center text-white font-bold text-lg">
                    {{ (comment.author || comment.user || 'U')[0].toUpperCase() }}
                  </div>
                </div>
                <div class="flex-1 min-w-0">
                  <div class="flex items-center space-x-2 mb-2">
                    <h4 class="text-sm font-semibold text-gray-900">{{ comment.author || comment.user || 'Anonymous' }}</h4>
                    <span :class="[
                      'px-2 py-1 rounded-full text-xs font-medium',
                      comment.vote === 'fake' || comment.voteType === 'FAKE' ? 'bg-red-100 text-red-700' :
                      comment.vote === 'notFake' || comment.voteType === 'NOT_FAKE' ? 'bg-green-100 text-green-700' :
                      'bg-gray-100 text-gray-700'
                    ]">
                      {{ comment.vote === 'fake' || comment.voteType === 'FAKE' ? '🚫 Fake' : 
                         comment.vote === 'notFake' || comment.voteType === 'NOT_FAKE' ? '✅ Real' : '❓ Undecided' }}
                    </span>
                    <span class="text-xs text-gray-500">{{ formatDate(comment.createdAt) }}</span>
                  </div>
                  <p class="text-gray-700 mb-3">{{ comment.text }}</p>
                  <img
                    v-if="comment.imageUrl"
                    :src="comment.imageUrl"
                    class="max-w-xs rounded-lg shadow-md hover:shadow-lg transition-shadow cursor-pointer mb-3"
                    @error="handleCommentImageError"
                    @click="openImageModal(comment.imageUrl)"
                  />
                  
                  <!-- Report Abuse Button -->
                  <div class="flex items-center gap-2 mt-3">
                    <button
                      @click="reportAbuse(comment.id)"
                      class="text-xs text-red-600 hover:text-red-800 hover:underline flex items-center gap-1"
                    >
                      <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path>
                      </svg>
                      Report Abuse
                    </button>
                    
                    <!-- Admin Delete Comment -->
                    <button
                      v-if="auth.isAdmin"
                      @click="adminDeleteComment(comment.id)"
                      class="text-xs text-red-600 hover:text-red-800 hover:underline ml-4"
                    >
                      🗑️ Delete (Admin)
                    </button>
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
  
  <!-- Not found -->
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
import { ref, computed, onMounted } from 'vue'
import { useNewsStore } from '../store/newsStore'
import { useAuthStore } from '../store/authStore'
import { useRoute, useRouter } from 'vue-router'
import VoteForm from '../components/VoteForm.vue'
import { newsService, voteService } from '../services/supabase'

const newsStore = useNewsStore()
const auth = useAuthStore()
const route = useRoute()
const router = useRouter()

const showImageModal = ref(false)
const modalImageUrl = ref('')
const commentSort = ref('newest')


let news = newsStore.selectedNews

// If not set, try to load it by ID from route
if (!news && route.params.id) {
  news = newsStore.newsList.find(n => String(n.id) === String(route.params.id))
}
// Fallback: fetch from Supabase when not in store
onMounted(async () => {
  if (!news && route.params.id) {
    try {
      const item = await newsService.getNewsById(Number(route.params.id))
      if (item) {
        // Map to local shape used by UI
        const voteCounts = await voteService.getVotes(item.id)
        news = {
          id: item.id,
          title: item.title,
          image: item.image_url || '/images/placeholder.jpg',
          status: (item.status || 'UNDECIDED').toLowerCase(),
          votes: { fake: voteCounts.fake, notFake: voteCounts.notFake },
          reporter: item.reporter?.full_name || item.reporter?.username || 'Anonymous',
          reporterId: item.reporter_id,
          createdAt: item.created_at,
          fullDetail: item.full_detail,
          comments: (item.comments || []).filter(c => !c.soft_deleted).map(c => ({
            id: c.id,
            author: c.author?.full_name || c.author?.username || 'Anonymous',
            user: c.author?.username,
            text: c.text,
            imageUrl: c.image_url || '',
            voteType: c.vote_type,
            createdAt: c.created_at,
            softDeleted: !!c.soft_deleted
          }))
        }
        // Set as selected for current view
        newsStore.selectedNews = news
      }
    } catch (e) {
      console.error('Failed to load news detail', e)
    }
  }
})

// Watch for news updates
const visibleComments = computed(() => {
  if (!news) return []
  return (news.comments || []).filter(c => !c.softDeleted)
})

const sortedComments = computed(() => {
  const comments = [...visibleComments.value]
  if (commentSort.value === 'newest') {
    return comments.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
  } else {
    return comments.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt))
  }
})

function formatDate(dateStr) {
  if (!dateStr) return 'N/A'
  const date = new Date(dateStr)
  const now = new Date()
  const diffInHours = (now - date) / (1000 * 60 * 60)

  if (diffInHours < 1) return 'Just now'
  if (diffInHours < 24) return `${Math.floor(diffInHours)}h ago`
  if (diffInHours < 48) return 'Yesterday'
  return date.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function goBack() {
  router.push('/')
}

function shareNews() {
  const url = window.location.href
  const title = news.title
  
  if (navigator.share) {
    navigator.share({
      title: title,
      text: `Check out this news: ${title}`,
      url: url
    }).catch(() => {
      copyToClipboard(url)
    })
  } else {
    copyToClipboard(url)
  }
}

function copyToClipboard(text) {
  navigator.clipboard.writeText(text).then(() => {
    alert('✅ Link copied to clipboard!')
  }).catch(() => {
    alert('Copy this link: ' + text)
  })
}

function reportAbuse(commentId) {
  if (!confirm('Report this comment as abusive?\n\nAdministrators will review it.')) return
  alert('✅ Abuse report submitted. Thank you for helping keep our community safe.')
  // In a real app, this would send a request to the backend
}

function adminDeleteComment(commentId) {
  if (!confirm('⚠️ Delete this comment?\n\nThis will remove it from the news article.')) return
  newsStore.adminSoftDeleteComment(news.id, commentId)
  alert('✅ Comment deleted successfully!')
}

function openImageModal(imageUrl) {
  modalImageUrl.value = imageUrl
  showImageModal.value = true
}

function closeImageModal() {
  showImageModal.value = false
  modalImageUrl.value = ''
}

function handleImageError(event) {
  event.target.src = '/images/placeholder.jpg'
}

function handleCommentImageError(event) {
  event.target.style.display = 'none'
}
</script>

<style scoped>

</style>
