<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <div class="bg-gradient-to-r from-purple-600 to-blue-600 text-white py-16">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h1 class="text-4xl md:text-5xl font-bold mb-4">📊 Statistics Dashboard</h1>
        <p class="text-xl opacity-90">Comprehensive insights into news verification trends</p>
      </div>
    </div>

    <!-- Main Content -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <!-- Overview Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-12">
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-red-500">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-12 h-12 bg-red-100 rounded-lg flex items-center justify-center">
                <span class="text-2xl">🚫</span>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">Fake News</p>
              <p class="text-3xl font-bold text-red-600">{{ fakeNewsCount }}</p>
              <p class="text-xs text-gray-500">{{ fakeNewsPercentage }}% of total</p>
            </div>
          </div>
        </div>
        
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-green-500">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                <span class="text-2xl">✅</span>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">Real News</p>
              <p class="text-3xl font-bold text-green-600">{{ realNewsCount }}</p>
              <p class="text-xs text-gray-500">{{ realNewsPercentage }}% of total</p>
            </div>
          </div>
        </div>
        
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-yellow-500">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
                <span class="text-2xl">❓</span>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">Undecided</p>
              <p class="text-3xl font-bold text-yellow-600">{{ undecidedNewsCount }}</p>
              <p class="text-xs text-gray-500">{{ undecidedNewsPercentage }}% of total</p>
            </div>
          </div>
        </div>
        
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-blue-500">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                <span class="text-2xl">💬</span>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">Total Comments</p>
              <p class="text-3xl font-bold text-blue-600">{{ totalComments }}</p>
              <p class="text-xs text-gray-500">Community engagement</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Charts Section -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-12">
        <!-- News Status Distribution -->
        <div class="bg-white rounded-xl shadow-lg p-8">
          <h3 class="text-2xl font-bold text-gray-900 mb-6">News Status Distribution</h3>
          <div class="space-y-4">
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-4 h-4 bg-red-500 rounded mr-3"></div>
                <span class="text-gray-700">Fake News</span>
              </div>
              <div class="flex items-center space-x-4">
                <div class="w-32 bg-gray-200 rounded-full h-2">
                  <div class="bg-red-500 h-2 rounded-full transition-all duration-500" :style="{ width: `${fakeNewsPercentage}%` }"></div>
                </div>
                <span class="text-sm font-semibold text-gray-900">{{ fakeNewsCount }}</span>
              </div>
            </div>
            
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-4 h-4 bg-green-500 rounded mr-3"></div>
                <span class="text-gray-700">Real News</span>
              </div>
              <div class="flex items-center space-x-4">
                <div class="w-32 bg-gray-200 rounded-full h-2">
                  <div class="bg-green-500 h-2 rounded-full transition-all duration-500" :style="{ width: `${realNewsPercentage}%` }"></div>
                </div>
                <span class="text-sm font-semibold text-gray-900">{{ realNewsCount }}</span>
              </div>
            </div>
            
            <div class="flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-4 h-4 bg-yellow-500 rounded mr-3"></div>
                <span class="text-gray-700">Undecided</span>
              </div>
              <div class="flex items-center space-x-4">
                <div class="w-32 bg-gray-200 rounded-full h-2">
                  <div class="bg-yellow-500 h-2 rounded-full transition-all duration-500" :style="{ width: `${undecidedNewsPercentage}%` }"></div>
                </div>
                <span class="text-sm font-semibold text-gray-900">{{ undecidedNewsCount }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Recent Activity -->
        <div class="bg-white rounded-xl shadow-lg p-8">
          <h3 class="text-2xl font-bold text-gray-900 mb-6">Recent Activity</h3>
          <div class="space-y-4">
            <div v-for="activity in recentActivity" :key="activity.id" class="flex items-center space-x-4 p-3 bg-gray-50 rounded-lg">
              <div class="flex-shrink-0">
                <div class="w-10 h-10 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center text-white font-semibold">
                  {{ activity.type === 'vote' ? '🗳️' : '💬' }}
                </div>
              </div>
              <div class="flex-1 min-w-0">
                <p class="text-sm text-gray-900">{{ activity.description }}</p>
                <p class="text-xs text-gray-500">{{ formatTimeAgo(activity.timestamp) }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Top Reporters -->
      <div class="bg-white rounded-xl shadow-lg p-8 mb-12">
        <h3 class="text-2xl font-bold text-gray-900 mb-6">Top Reporters</h3>
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Reporter</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Articles</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Fake</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Real</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Undecided</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="reporter in topReporters" :key="reporter.name" class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex items-center">
                    <div class="w-8 h-8 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center text-white font-semibold text-sm mr-3">
                      {{ reporter.name.charAt(0).toUpperCase() }}
                    </div>
                    <span class="text-sm font-medium text-gray-900">{{ reporter.name }}</span>
                  </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ reporter.total }}</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-red-600">{{ reporter.fake }}</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-green-600">{{ reporter.real }}</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-yellow-600">{{ reporter.undecided }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Trending Topics -->
      <div class="bg-white rounded-xl shadow-lg p-8">
        <h3 class="text-2xl font-bold text-gray-900 mb-6">Trending Topics</h3>
        <div class="flex flex-wrap gap-3">
          <span 
            v-for="topic in trendingTopics" 
            :key="topic" 
            class="px-4 py-2 bg-blue-100 text-blue-800 rounded-full text-sm font-medium hover:bg-blue-200 transition-colors cursor-pointer"
          >
            #{{ topic }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useNewsStore } from '../store/newsStore'

const store = useNewsStore()

// Statistics
const fakeNewsCount = computed(() => store.newsList.filter(n => n.status === 'fake').length)
const realNewsCount = computed(() => store.newsList.filter(n => n.status === 'notFake').length)
const undecidedNewsCount = computed(() => store.newsList.filter(n => n.status === 'undecided').length)
const totalNews = computed(() => store.newsList.length)

const fakeNewsPercentage = computed(() => 
  totalNews.value > 0 ? Math.round((fakeNewsCount.value / totalNews.value) * 100) : 0
)
const realNewsPercentage = computed(() => 
  totalNews.value > 0 ? Math.round((realNewsCount.value / totalNews.value) * 100) : 0
)
const undecidedNewsPercentage = computed(() => 
  totalNews.value > 0 ? Math.round((undecidedNewsCount.value / totalNews.value) * 100) : 0
)

const totalComments = computed(() => 
  store.newsList.reduce((total, news) => total + (news.comments?.length || 0), 0)
)

// Top reporters
const topReporters = computed(() => {
  const reporterStats = {}
  
  store.newsList.forEach(news => {
    if (!reporterStats[news.reporter]) {
      reporterStats[news.reporter] = {
        name: news.reporter,
        total: 0,
        fake: 0,
        real: 0,
        undecided: 0
      }
    }
    
    reporterStats[news.reporter].total++
    if (news.status === 'fake') reporterStats[news.reporter].fake++
    else if (news.status === 'notFake') reporterStats[news.reporter].real++
    else reporterStats[news.reporter].undecided++
  })
  
  return Object.values(reporterStats)
    .sort((a, b) => b.total - a.total)
    .slice(0, 10)
})

// Recent activity
const recentActivity = computed(() => {
  const activities = []
  
  store.newsList.forEach(news => {
    // Add news creation activity
    activities.push({
      id: `news-${news.id}`,
      type: 'news',
      description: `New article: "${news.title}"`,
      timestamp: new Date(news.dateTime)
    })
    
    // Add comment activities
    if (news.comments) {
      news.comments.forEach((comment, index) => {
        activities.push({
          id: `comment-${news.id}-${index}`,
          type: 'comment',
          description: `${comment.user} commented on "${news.title}"`,
          timestamp: new Date(news.dateTime) // In real app, comments would have their own timestamps
        })
      })
    }
  })
  
  return activities
    .sort((a, b) => b.timestamp - a.timestamp)
    .slice(0, 10)
})

// Trending topics (simplified - in real app would use more sophisticated analysis)
const trendingTopics = computed(() => {
  const words = store.newsList
    .flatMap(news => [
      ...news.title.toLowerCase().split(' '),
      ...news.shortDetail.toLowerCase().split(' ')
    ])
    .filter(word => word.length > 3)
    .filter(word => !['this', 'that', 'with', 'from', 'they', 'have', 'been', 'were', 'said', 'will', 'been', 'were', 'said', 'will', 'news', 'fake', 'real', 'thai', 'bangkok'].includes(word))
  
  const wordCount = {}
  words.forEach(word => {
    wordCount[word] = (wordCount[word] || 0) + 1
  })
  
  return Object.entries(wordCount)
    .sort(([,a], [,b]) => b - a)
    .slice(0, 15)
    .map(([word]) => word)
})

function formatTimeAgo(date) {
  const now = new Date()
  const diffInMinutes = Math.floor((now - date) / (1000 * 60))
  
  if (diffInMinutes < 1) return 'Just now'
  if (diffInMinutes < 60) return `${diffInMinutes}m ago`
  if (diffInMinutes < 1440) return `${Math.floor(diffInMinutes / 60)}h ago`
  return `${Math.floor(diffInMinutes / 1440)}d ago`
}
</script>
