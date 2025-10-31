<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 py-8">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
      <div v-if="user" class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
        <!-- Profile Header -->
        <div class="bg-gradient-to-r from-blue-600 to-purple-600 text-white p-8">
          <div class="flex flex-col md:flex-row items-center md:items-start gap-6">
            <img 
              :src="user.imageUrl || '/images/avatar-default.png'" 
              :alt="user.name"
              class="w-32 h-32 rounded-full object-cover border-4 border-white shadow-xl"
              @error="handleImageError"
            />
            <div class="flex-1 text-center md:text-left">
              <h1 class="text-3xl font-bold mb-2">{{ user.name || user.username }}</h1>
              <p class="text-blue-100 mb-4">@{{ user.username }}</p>
              <div class="flex flex-wrap gap-3 justify-center md:justify-start">
                <span :class="[
                  'px-4 py-2 rounded-full text-sm font-semibold',
                  user.role === 'ADMIN' ? 'bg-purple-200 text-purple-900' :
                  user.role === 'MEMBER' ? 'bg-blue-200 text-blue-900' :
                  'bg-gray-200 text-gray-900'
                ]">
                  {{ user.role }}
                </span>
                <span v-if="user.email" class="px-4 py-2 bg-white/20 text-white rounded-full text-sm">
                  📧 {{ user.email }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- User Statistics -->
        <div class="p-8">
          <h2 class="text-2xl font-bold text-gray-900 mb-6">Statistics</h2>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div class="bg-blue-50 rounded-xl p-6 text-center border-2 border-blue-200">
              <div class="text-3xl font-bold text-blue-600 mb-2">{{ userNewsCount }}</div>
              <div class="text-sm text-gray-600 font-medium">News Articles</div>
            </div>
            <div class="bg-green-50 rounded-xl p-6 text-center border-2 border-green-200">
              <div class="text-3xl font-bold text-green-600 mb-2">{{ userCommentsCount }}</div>
              <div class="text-sm text-gray-600 font-medium">Comments</div>
            </div>
            <div class="bg-purple-50 rounded-xl p-6 text-center border-2 border-purple-200">
              <div class="text-3xl font-bold text-purple-600 mb-2">{{ userVotesCount }}</div>
              <div class="text-sm text-gray-600 font-medium">Votes Cast</div>
            </div>
          </div>

          <!-- Role Upgrade (Admin Only) -->
          <div v-if="auth.isAdmin && user.role === 'READER'" class="bg-yellow-50 border-2 border-yellow-200 rounded-xl p-6 mb-8">
            <h3 class="text-lg font-bold text-gray-900 mb-3">Upgrade User Role</h3>
            <p class="text-gray-700 mb-4">This user is currently a READER. Upgrade them to MEMBER to allow news submissions.</p>
            <button
              @click="upgradeToMember"
              class="px-6 py-3 bg-green-600 hover:bg-green-700 text-white font-semibold rounded-lg transition-colors"
            >
              ↑ Upgrade to MEMBER
            </button>
          </div>

          <!-- User's News Articles -->
          <div class="mt-8">
            <h2 class="text-2xl font-bold text-gray-900 mb-6">Published News Articles</h2>
            <div v-if="userNews.length === 0" class="text-center py-12 bg-gray-50 rounded-xl">
              <div class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
                <span class="text-3xl">📰</span>
              </div>
              <p class="text-gray-500">No news articles published yet</p>
            </div>
            <div v-else class="space-y-4">
              <div
                v-for="newsItem in userNews"
                :key="newsItem.id"
                class="border-2 border-gray-200 rounded-xl p-6 hover:border-blue-300 transition-colors cursor-pointer"
                @click="goToNews(newsItem.id)"
              >
                <div class="flex gap-4">
                  <img 
                    :src="newsItem.image || '/images/placeholder.jpg'" 
                    :alt="newsItem.title"
                    class="w-24 h-24 object-cover rounded-lg border-2 border-gray-200"
                  />
                  <div class="flex-1">
                    <h3 class="text-lg font-bold text-gray-900 mb-2">{{ newsItem.title }}</h3>
                    <p class="text-gray-600 text-sm mb-3 line-clamp-2">{{ newsItem.shortDetail }}</p>
                    <div class="flex items-center gap-4 text-sm">
                      <span :class="[
                        'px-3 py-1 rounded-full text-xs font-semibold',
                        newsItem.status === 'fake' ? 'bg-red-100 text-red-700' :
                        newsItem.status === 'notFake' ? 'bg-green-100 text-green-700' :
                        'bg-yellow-100 text-yellow-700'
                      ]">
                        {{ newsItem.status === 'fake' ? '🚫 FAKE' : newsItem.status === 'notFake' ? '✅ REAL' : '❓ UNDECIDED' }}
                      </span>
                      <span class="text-gray-500">{{ formatDate(newsItem.createdAt || newsItem.dateTime) }}</span>
                      <span class="text-gray-500">💬 {{ newsItem.comments?.length || 0 }} comments</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- User Not Found -->
      <div v-else class="bg-white rounded-2xl shadow-xl border border-gray-100 p-12 text-center">
        <div class="w-24 h-24 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
          <span class="text-4xl">👤</span>
        </div>
        <h3 class="text-xl font-bold text-gray-900 mb-2">User Not Found</h3>
        <p class="text-gray-500 mb-6">The user you're looking for doesn't exist.</p>
        <router-link
          to="/"
          class="inline-block px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
        >
          Back to Home
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../store/authStore'
import { useNewsStore } from '../store/newsStore'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
const newsStore = useNewsStore()

const userId = Number(route.params.id)
const user = computed(() => auth.findUserById(userId))

const userNews = computed(() => {
  if (!user.value) return []
  return newsStore.newsList
    .filter(n => (n.reporterId === userId || n.reporter === user.value?.name) && !n.softDeleted)
    .sort((a, b) => new Date(b.createdAt || b.dateTime) - new Date(a.createdAt || a.dateTime))
})

const userNewsCount = computed(() => userNews.value.length)

const userCommentsCount = computed(() => {
  if (!user.value) return 0
  return newsStore.newsList
    .filter(n => !n.softDeleted)
    .reduce((sum, n) => {
      return sum + (n.comments?.filter(c => 
        (c.author === user.value.name || c.user === user.value.name) && !c.softDeleted
      ).length || 0)
    }, 0)
})

const userVotesCount = computed(() => {
  // Estimate based on comments with votes
  return userCommentsCount.value // Simplified
})

function upgradeToMember() {
  if (!confirm(`Upgrade ${user.value.name} to MEMBER role?\n\nMembers can submit news articles.`)) return
  const updated = auth.upgradeToMember(userId)
  if (updated) {
    alert('✅ User upgraded to MEMBER successfully!')
  } else {
    alert('❌ Failed to upgrade user')
  }
}

function goToNews(newsId) {
  router.push(`/news/${newsId}`)
}

function formatDate(dateStr) {
  if (!dateStr) return 'N/A'
  try {
    const date = new Date(dateStr)
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    })
  } catch (e) {
    return dateStr
  }
}

function handleImageError(event) {
  event.target.src = '/images/avatar-default.png'
}
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
