<template>
  <div class="min-h-screen bg-gradient-to-br from-purple-50 via-white to-blue-50 py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Header -->
      <div class="bg-gradient-to-r from-purple-600 to-blue-600 text-white rounded-2xl shadow-xl p-6 mb-8">
        <div class="flex items-center justify-between flex-wrap gap-4">
      <div>
            <h1 class="text-3xl font-bold mb-2">Admin Control Panel</h1>
            <p class="text-purple-100">Manage users, news, and comments</p>
          </div>
          <button 
            @click="refresh" 
            class="px-6 py-3 bg-white/20 hover:bg-white/30 text-white font-semibold rounded-xl transition-all backdrop-blur-sm"
          >
            🔄 Refresh Data
          </button>
        </div>
      </div>

      <!-- Tabs -->
      <div class="flex flex-wrap gap-2 mb-6 border-b border-gray-200">
        <button
          v-for="tab in tabs"
          :key="tab.id"
          @click="activeTab = tab.id"
          :class="[
            'px-6 py-3 font-semibold rounded-t-lg transition-all',
            activeTab === tab.id
              ? 'bg-white text-blue-600 border-t-2 border-x-2 border-blue-600'
              : 'text-gray-600 hover:text-blue-600 hover:bg-gray-50'
          ]"
        >
          {{ tab.label }}
        </button>
    </div>

      <!-- Users Tab -->
      <div v-if="activeTab === 'users'" class="bg-white rounded-2xl shadow-xl border border-gray-100 p-6">
        <h2 class="text-2xl font-bold mb-6 text-gray-900">User Management</h2>
        
        <div class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-gray-50 border-b-2 border-gray-200">
                <th class="px-4 py-3 text-left text-sm font-semibold text-gray-700">User</th>
                <th class="px-4 py-3 text-left text-sm font-semibold text-gray-700">Email</th>
                <th class="px-4 py-3 text-left text-sm font-semibold text-gray-700">Role</th>
                <th class="px-4 py-3 text-left text-sm font-semibold text-gray-700">Actions</th>
              </tr>
            </thead>
        <tbody>
              <tr 
                v-for="u in users" 
                :key="u.id" 
                class="border-b border-gray-100 hover:bg-gray-50 transition-colors"
              >
                <td class="px-4 py-4">
                  <div class="flex items-center gap-3">
                    <img 
                      :src="u.imageUrl || '/images/avatar-default.png'" 
                      :alt="u.name"
                      class="w-10 h-10 rounded-full object-cover border-2 border-gray-200"
                    />
                    <div>
                      <div class="font-semibold text-gray-900">{{ u.name || u.username }}</div>
                      <div class="text-sm text-gray-500">@{{ u.username }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-4 py-4 text-gray-700">{{ u.email }}</td>
                <td class="px-4 py-4">
                  <span :class="[
                    'px-3 py-1 rounded-full text-xs font-semibold',
                    u.role === 'ADMIN' ? 'bg-purple-100 text-purple-700' :
                    u.role === 'MEMBER' ? 'bg-blue-100 text-blue-700' :
                    'bg-gray-100 text-gray-700'
                  ]">
                    {{ u.role }}
                  </span>
                </td>
                <td class="px-4 py-4">
                  <div class="flex gap-2">
                    <button
                      v-if="u.role === 'READER'"
                      @click="upgradeToMember(u.id)"
                      class="px-4 py-2 bg-green-600 hover:bg-green-700 text-white text-sm font-semibold rounded-lg transition-colors"
                    >
                      ↑ Member
                    </button>
                    <button
                      v-if="u.role !== 'ADMIN' && u.id !== auth.user?.id"
                      @click="upgradeToAdmin(u.id)"
                      class="px-4 py-2 bg-purple-600 hover:bg-purple-700 text-white text-sm font-semibold rounded-lg transition-colors"
                    >
                      ↑ Admin
                    </button>
                    <button
                      v-if="u.role === 'MEMBER'"
                      @click="downgradeToReader(u.id)"
                      class="px-4 py-2 bg-gray-400 hover:bg-gray-500 text-white text-sm font-semibold rounded-lg transition-colors"
                    >
                      ↓ Reader
                    </button>
                    <button
                      v-if="u.role === 'ADMIN' && u.id !== auth.user?.id"
                      @click="downgradeToReader(u.id)"
                      class="px-4 py-2 bg-red-600 hover:bg-red-700 text-white text-sm font-semibold rounded-lg transition-colors"
                    >
                      ↓ Remove Admin
                    </button>
                  </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    </div>

      <!-- News Management Tab -->
      <div v-if="activeTab === 'news'" class="bg-white rounded-2xl shadow-xl border border-gray-100 p-6">
        <h2 class="text-2xl font-bold mb-6 text-gray-900">News Management</h2>
        
        <!-- Search Bar -->
        <div class="mb-6">
          <div class="relative">
            <input
              v-model="newsSearchQuery"
              type="text"
              placeholder="Search news by title, reporter, or content..."
              class="w-full py-3 pr-12 border-2 border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all placeholder-gray-400"
              style="padding-left: 3.75rem;"
            />
            <div class="absolute left-4 top-1/2 transform -translate-y-1/2 pointer-events-none">
              <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
              </svg>
            </div>
            <button
              v-if="newsSearchQuery"
              @click="newsSearchQuery = ''"
              class="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-red-500 transition-colors"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
              </svg>
            </button>
          </div>
          <p v-if="newsSearchQuery" class="mt-2 text-sm text-gray-600">
            Found {{ filteredNews.length }} result(s) for "{{ newsSearchQuery }}"
          </p>
        </div>

        <!-- Filter Buttons -->
        <div class="mb-4 flex gap-2 flex-wrap">
          <button
            @click="newsFilter = 'all'"
            :class="['px-4 py-2 rounded-lg font-semibold transition-colors', newsFilter === 'all' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']"
          >
            All News
          </button>
          <button
            @click="newsFilter = 'active'"
            :class="['px-4 py-2 rounded-lg font-semibold transition-colors', newsFilter === 'active' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']"
          >
            Active
          </button>
          <button
            @click="newsFilter = 'removed'"
            :class="['px-4 py-2 rounded-lg font-semibold transition-colors', newsFilter === 'removed' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']"
          >
            Removed
          </button>
        </div>

        <!-- No Results Message -->
        <div v-if="filteredNews.length === 0" class="text-center py-12 bg-gray-50 rounded-xl">
          <div class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
            <span class="text-3xl">🔍</span>
          </div>
          <h3 class="text-lg font-medium text-gray-900 mb-2">No news found</h3>
          <p class="text-gray-500">
            {{ newsSearchQuery ? `No news matches "${newsSearchQuery}"` : 'Try adjusting your filters' }}
          </p>
    </div>

        <!-- News List -->
        <div v-else class="space-y-4">
          <div 
            v-for="n in filteredNews" 
            :key="n.id" 
            :class="[
              'p-4 rounded-xl border-2 transition-all',
              n.softDeleted ? 'bg-red-50 border-red-200' : 'bg-white border-gray-200 hover:border-blue-300'
            ]"
          >
            <div class="flex gap-4">
              <img 
                :src="n.image || '/images/placeholder.jpg'" 
                :alt="n.title"
                class="w-24 h-24 object-cover rounded-lg border-2 border-gray-200"
              />
              <div class="flex-1">
                <div class="flex items-start justify-between mb-2">
                  <div>
                    <h3 class="text-lg font-bold text-gray-900 mb-1">
                      {{ n.title }}
                      <span v-if="n.softDeleted" class="ml-2 px-2 py-1 bg-red-100 text-red-700 text-xs font-semibold rounded-full">
                        [REMOVED]
                      </span>
                    </h3>
                    <p class="text-sm text-gray-600 mb-2">{{ n.shortDetail }}</p>
                    <div class="flex items-center gap-4 text-sm text-gray-500 flex-wrap">
                      <span class="flex items-center gap-1">
                        <span class="font-medium text-gray-700">Reporter:</span>
                        <span>{{ n.reporter }}</span>
                      </span>
                      <span class="flex items-center gap-1">
                        <span class="font-medium text-gray-700">Time Report:</span>
                        <span>{{ formatDate(n.createdAt || n.dateTime) }}</span>
                      </span>
                      <span :class="[
                        'px-2 py-1 rounded-full text-xs font-semibold',
                        n.status === 'fake' ? 'bg-red-100 text-red-700' :
                        n.status === 'notFake' ? 'bg-green-100 text-green-700' :
                        'bg-yellow-100 text-yellow-700'
                      ]">
                        {{ n.status === 'fake' ? 'FAKE' : n.status === 'notFake' ? 'REAL' : 'UNDECIDED' }}
                      </span>
                    </div>
                  </div>
                </div>
                <div class="flex gap-2">
                  <button
                    @click="toggleRemoveNews(n)"
                    :class="[
                      'px-4 py-2 rounded-lg font-semibold transition-colors',
                      n.softDeleted 
                        ? 'bg-green-600 hover:bg-green-700 text-white' 
                        : 'bg-red-600 hover:bg-red-700 text-white'
                    ]"
                  >
                    {{ n.softDeleted ? '✓ Restore' : '🗑️ Remove' }}
                  </button>
                  <router-link
                    :to="`/news/${n.id}`"
                    class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg transition-colors"
                  >
                    👁️ View
                  </router-link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Comments Management Tab -->
      <div v-if="activeTab === 'comments'" class="bg-white rounded-2xl shadow-xl border border-gray-100 p-6">
        <h2 class="text-2xl font-bold mb-6 text-gray-900">Comment Management</h2>
        
        <div class="space-y-6">
          <div 
            v-for="newsItem in newsWithRemovedComments" 
            :key="newsItem.id"
            class="border-2 border-gray-200 rounded-xl p-4 bg-gray-50"
          >
            <h3 class="text-lg font-bold text-gray-900 mb-4">
              {{ newsItem.title }}
            </h3>
            
            <div 
              v-for="comment in newsItem.comments.filter(c => c.softDeleted)" 
              :key="comment.id"
              class="bg-white rounded-lg p-4 mb-4 border-l-4 border-red-500"
            >
              <div class="flex items-start justify-between mb-2">
                <div class="flex items-center gap-3">
                  <div class="w-10 h-10 bg-gradient-to-r from-red-500 to-pink-500 rounded-full flex items-center justify-center text-white font-bold">
                    {{ (comment.author || comment.user || 'U')[0].toUpperCase() }}
                  </div>
                  <div>
                    <div class="font-semibold text-gray-900">{{ comment.author || comment.user || 'Anonymous' }}</div>
                    <div class="text-xs text-gray-500">{{ formatDate(comment.createdAt) }}</div>
                  </div>
                </div>
                <span class="px-2 py-1 bg-red-100 text-red-700 text-xs font-semibold rounded-full">
                  REMOVED
                </span>
              </div>
              
              <p class="text-gray-700 mb-3 mt-2">{{ comment.text }}</p>
              
              <div class="flex gap-2 justify-end">
                <button
                  @click="restoreComment(newsItem.id, comment.id)"
                  class="px-4 py-2 bg-green-600 hover:bg-green-700 text-white text-sm font-semibold rounded-lg transition-colors"
                >
                  ✓ Restore
                </button>
                <button
                  @click="hardDeleteComment(newsItem.id, comment.id)"
                  class="px-4 py-2 bg-red-600 hover:bg-red-700 text-white text-sm font-semibold rounded-lg transition-colors"
                >
                  🗑️ Delete Permanently
                </button>
        </div>
      </div>
    </div>

          <div v-if="newsWithRemovedComments.length === 0" class="text-center py-12">
            <div class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
              <span class="text-3xl">💬</span>
            </div>
            <p class="text-gray-500">No removed comments to manage</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useAuthStore } from '../store/authStore'
import { useNewsStore } from '../store/newsStore'

    const auth = useAuthStore()
const newsStore = useNewsStore()

const activeTab = ref('users')
const newsFilter = ref('all')
const newsSearchQuery = ref('')

const tabs = [
  { id: 'users', label: '👥 Users' },
  { id: 'news', label: '📰 News' },
  { id: 'comments', label: '💬 Comments' }
]

const users = computed(() => auth.users)
const allNews = computed(() => newsStore.newsList)
const newsWithRemovedComments = computed(() => 
  newsStore.newsList.filter(n => (n.comments || []).some(c => c.softDeleted))
)

const filteredNews = computed(() => {
  let list = allNews.value
  
  // Apply filter
  if (newsFilter.value === 'active') {
    list = list.filter(n => !n.softDeleted)
  } else if (newsFilter.value === 'removed') {
    list = list.filter(n => n.softDeleted)
  }
  
  // Apply search query
  if (newsSearchQuery.value.trim()) {
    const query = newsSearchQuery.value.toLowerCase().trim()
    list = list.filter(n => {
      return (
        n.title?.toLowerCase().includes(query) ||
        n.shortDetail?.toLowerCase().includes(query) ||
        n.detail?.toLowerCase().includes(query) ||
        n.fullDetail?.toLowerCase().includes(query) ||
        n.reporter?.toLowerCase().includes(query) ||
        (n.status && n.status.toLowerCase().includes(query))
      )
    })
  }
  
  return list
})

function refresh() {
  alert('✅ Data refreshed!')
}

function upgradeToMember(id) {
  if (!confirm(`Upgrade this user to MEMBER role?\n\nMembers can submit news articles.`)) return
  const user = auth.upgradeToMember(id)
  if (user) {
    alert(`✅ User upgraded to MEMBER successfully!`)
  } else {
    alert('❌ Failed to upgrade user')
  }
}

function upgradeToAdmin(id) {
  if (!confirm(`⚠️ Make this user an ADMIN?\n\nAdmins have full control over the system. This action cannot be easily undone.`)) return
  const user = auth.upgradeToAdmin(id)
  if (user) {
    alert(`✅ User upgraded to ADMIN successfully!`)
  } else {
    alert('❌ Failed to upgrade user')
  }
}

function downgradeToReader(id) {
  const user = auth.users.find(u => u.id === id)
  if (!user) return
  
  const roleText = user.role === 'ADMIN' ? 'ADMIN' : 'MEMBER'
  if (!confirm(`Downgrade this user from ${roleText} to READER?\n\nThis will revoke their ability to ${user.role === 'ADMIN' ? 'manage the system and ' : ''}submit news.`)) return
  
  const updatedUser = auth.downgradeToReader(id)
  if (updatedUser) {
    alert(`✅ User downgraded to READER successfully!`)
  } else {
    alert('❌ Failed to downgrade user')
  }
}

function toggleRemoveNews(news) {
  if (news.softDeleted) {
    if (!confirm(`Restore this news article?\n\n"${news.title}"\n\nIt will become visible to all users again.`)) return
    newsStore.adminRestoreNews(news.id)
    alert('✅ News article restored successfully!')
  } else {
    if (!confirm(`⚠️ Remove this news article?\n\n"${news.title}"\n\nRegular users will no longer see this article. Only admins can view it.`)) return
    newsStore.adminSoftDeleteNews(news.id)
    alert('✅ News article removed successfully!')
  }
}

function restoreComment(newsId, commentId) {
  if (!confirm('Restore this comment?\n\nIt will become visible again and the score will be recalculated.')) return
  
  const news = newsStore.newsList.find(x => x.id === newsId)
  if (!news) return
  
  const comment = news.comments.find(x => x.id === commentId)
  if (!comment) return
  
  comment.softDeleted = false
  alert('✅ Comment restored successfully!')
}

function hardDeleteComment(newsId, commentId) {
  if (!confirm('⚠️ Permanently delete this comment?\n\nThis action cannot be undone. The comment will be removed from the database.')) return
  
  const success = newsStore.adminHardDeleteComment(newsId, commentId)
  if (success) {
    alert('✅ Comment deleted permanently!')
  } else {
    alert('❌ Failed to delete comment')
  }
}

function formatDate(dateStr) {
  if (!dateStr) return 'N/A'
  try {
    const date = new Date(dateStr)
    return date.toLocaleString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch (e) {
    return dateStr
  }
}
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
