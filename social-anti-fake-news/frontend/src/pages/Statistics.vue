<template>
  <div class="min-h-screen bg-gradient-to-br from-purple-50 via-white to-blue-50 py-8">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Header -->
      <div class="bg-gradient-to-r from-purple-600 to-blue-600 text-white rounded-2xl shadow-xl p-8 mb-8 text-center">
        <h1 class="text-4xl font-bold mb-3">📊 Statistics Dashboard</h1>
        <p class="text-purple-100 text-lg">Comprehensive insights into our news verification community</p>
      </div>

      <!-- Statistics Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <!-- Total News -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-blue-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-500 text-sm font-medium mb-1">Total News</p>
              <p class="text-3xl font-bold text-gray-900">{{ totalNews }}</p>
            </div>
            <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
              <span class="text-2xl">📰</span>
            </div>
          </div>
        </div>

        <!-- Fake News -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-red-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-500 text-sm font-medium mb-1">Fake News</p>
              <p class="text-3xl font-bold text-red-600">{{ fakeNewsCount }}</p>
            </div>
            <div class="w-12 h-12 bg-red-100 rounded-full flex items-center justify-center">
              <span class="text-2xl">🚫</span>
            </div>
          </div>
        </div>

        <!-- Real News -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-green-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-500 text-sm font-medium mb-1">Real News</p>
              <p class="text-3xl font-bold text-green-600">{{ realNewsCount }}</p>
            </div>
            <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center">
              <span class="text-2xl">✅</span>
            </div>
          </div>
        </div>

        <!-- Undecided -->
        <div class="bg-white rounded-xl shadow-lg p-6 border-l-4 border-yellow-500">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-500 text-sm font-medium mb-1">Undecided</p>
              <p class="text-3xl font-bold text-yellow-600">{{ undecidedCount }}</p>
            </div>
            <div class="w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center">
              <span class="text-2xl">❓</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Top Reporters Ranking -->
      <div class="bg-white rounded-2xl shadow-xl border border-gray-100 p-8 mb-8">
        <h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
          <span class="mr-3">🏆</span>
          Top Reporters Ranking
        </h2>
        <p class="text-gray-600 mb-6">Ranked by number of news articles submitted</p>
        
        <div class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-gray-50 border-b-2 border-gray-200">
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">Rank</th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">Reporter</th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">News Count</th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">Fake / Real</th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-gray-700">Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr 
                v-for="(reporter, index) in topReporters" 
                :key="reporter.id"
                class="border-b border-gray-100 hover:bg-gray-50 transition-colors"
              >
                <td class="px-6 py-4">
                  <div class="flex items-center">
                    <span :class="[
                      'w-8 h-8 rounded-full flex items-center justify-center font-bold text-sm',
                      index === 0 ? 'bg-yellow-100 text-yellow-700' :
                      index === 1 ? 'bg-gray-100 text-gray-700' :
                      index === 2 ? 'bg-orange-100 text-orange-700' :
                      'bg-blue-100 text-blue-700'
                    ]">
                      {{ index + 1 }}
                    </span>
                  </div>
                </td>
                <td class="px-6 py-4">
                  <div class="flex items-center gap-3">
                    <img 
                      :src="reporter.imageUrl || '/images/avatar-default.png'" 
                      :alt="reporter.name"
                      class="w-10 h-10 rounded-full object-cover border-2 border-gray-200"
                    />
                    <div>
                      <div class="font-semibold text-gray-900">{{ reporter.name }}</div>
                      <div class="text-sm text-gray-500">@{{ reporter.username }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4">
                  <span class="text-lg font-bold text-gray-900">{{ reporter.count }}</span>
                  <span class="text-sm text-gray-500 ml-1">articles</span>
                </td>
                <td class="px-6 py-4">
                  <div class="flex items-center gap-2">
                    <span class="px-2 py-1 bg-red-100 text-red-700 rounded-full text-xs font-semibold">
                      🚫 {{ reporter.fakeCount }}
                    </span>
                    <span class="px-2 py-1 bg-green-100 text-green-700 rounded-full text-xs font-semibold">
                      ✅ {{ reporter.realCount }}
                    </span>
                  </div>
                </td>
                <td class="px-6 py-4">
                  <router-link
                    :to="`/user/${reporter.id}`"
                    class="text-blue-600 hover:text-blue-800 hover:underline font-medium"
                  >
                    View Profile
                  </router-link>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div v-if="topReporters.length === 0" class="text-center py-12">
          <div class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
            <span class="text-3xl">📊</span>
          </div>
          <p class="text-gray-500">No reporters data available yet</p>
        </div>
      </div>

      <!-- Additional Stats -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <!-- Vote Statistics -->
        <div class="bg-white rounded-2xl shadow-xl border border-gray-100 p-8">
          <h3 class="text-xl font-bold text-gray-900 mb-6">Vote Statistics</h3>
          <div class="space-y-4">
            <div>
              <div class="flex justify-between mb-2">
                <span class="text-sm font-medium text-gray-700">Total Fake Votes</span>
                <span class="text-sm font-bold text-red-600">{{ totalFakeVotes }}</span>
              </div>
              <div class="w-full bg-gray-200 rounded-full h-2">
                <div 
                  class="bg-red-600 h-2 rounded-full transition-all"
                  :style="{ width: `${votePercentage.fake}%` }"
                ></div>
              </div>
            </div>
            <div>
              <div class="flex justify-between mb-2">
                <span class="text-sm font-medium text-gray-700">Total Real Votes</span>
                <span class="text-sm font-bold text-green-600">{{ totalRealVotes }}</span>
              </div>
              <div class="w-full bg-gray-200 rounded-full h-2">
                <div 
                  class="bg-green-600 h-2 rounded-full transition-all"
                  :style="{ width: `${votePercentage.real}%` }"
                ></div>
              </div>
            </div>
          </div>
        </div>

        <!-- Comment Statistics -->
        <div class="bg-white rounded-2xl shadow-xl border border-gray-100 p-8">
          <h3 class="text-xl font-bold text-gray-900 mb-6">Comment Statistics</h3>
          <div class="space-y-4">
            <div class="flex items-center justify-between p-4 bg-blue-50 rounded-lg">
              <span class="text-sm font-medium text-gray-700">Total Comments</span>
              <span class="text-2xl font-bold text-blue-600">{{ totalComments }}</span>
            </div>
            <div class="flex items-center justify-between p-4 bg-green-50 rounded-lg">
              <span class="text-sm font-medium text-gray-700">Active Comments</span>
              <span class="text-2xl font-bold text-green-600">{{ activeComments }}</span>
            </div>
            <div class="flex items-center justify-between p-4 bg-red-50 rounded-lg">
              <span class="text-sm font-medium text-gray-700">Removed Comments</span>
              <span class="text-2xl font-bold text-red-600">{{ removedComments }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useNewsStore } from '../store/newsStore'
import { useAuthStore } from '../store/authStore'

const newsStore = useNewsStore()
const auth = useAuthStore()

const totalNews = computed(() => newsStore.newsList.filter(n => !n.softDeleted).length)
const fakeNewsCount = computed(() => newsStore.newsList.filter(n => n.status === 'fake' && !n.softDeleted).length)
const realNewsCount = computed(() => newsStore.newsList.filter(n => n.status === 'notFake' && !n.softDeleted).length)
const undecidedCount = computed(() => newsStore.newsList.filter(n => n.status === 'undecided' && !n.softDeleted).length)

// Top Reporters
const topReporters = computed(() => {
  const reporterMap = new Map()
  
  newsStore.newsList
    .filter(n => !n.softDeleted)
    .forEach(news => {
      const reporterId = news.reporterId || news.reporter
      const reporterName = news.reporter || 'Anonymous'
      
      if (!reporterMap.has(reporterId)) {
        const user = auth.findUserById(reporterId) || {
          id: reporterId,
          name: reporterName,
          username: reporterName.toLowerCase().replace(/\s+/g, ''),
          imageUrl: ''
        }
        
        reporterMap.set(reporterId, {
          id: reporterId,
          name: user.name || reporterName,
          username: user.username || reporterName.toLowerCase().replace(/\s+/g, ''),
          imageUrl: user.imageUrl || '',
          count: 0,
          fakeCount: 0,
          realCount: 0
        })
      }
      
      const reporter = reporterMap.get(reporterId)
      reporter.count++
      if (news.status === 'fake') reporter.fakeCount++
      if (news.status === 'notFake') reporter.realCount++
    })
  
  return Array.from(reporterMap.values())
    .sort((a, b) => b.count - a.count)
    .slice(0, 10)
})

// Vote Statistics
const totalFakeVotes = computed(() => {
  return newsStore.newsList
    .filter(n => !n.softDeleted)
    .reduce((sum, n) => sum + (n.votes?.fake || 0), 0)
})

const totalRealVotes = computed(() => {
  return newsStore.newsList
    .filter(n => !n.softDeleted)
    .reduce((sum, n) => sum + (n.votes?.notFake || 0), 0)
})

const votePercentage = computed(() => {
  const total = totalFakeVotes.value + totalRealVotes.value
  if (total === 0) return { fake: 0, real: 0 }
  return {
    fake: Math.round((totalFakeVotes.value / total) * 100),
    real: Math.round((totalRealVotes.value / total) * 100)
  }
})

// Comment Statistics
const totalComments = computed(() => {
  return newsStore.newsList
    .filter(n => !n.softDeleted)
    .reduce((sum, n) => sum + (n.comments?.length || 0), 0)
})

const activeComments = computed(() => {
  return newsStore.newsList
    .filter(n => !n.softDeleted)
    .reduce((sum, n) => sum + (n.comments?.filter(c => !c.softDeleted).length || 0), 0)
})

const removedComments = computed(() => {
  return newsStore.newsList
    .filter(n => !n.softDeleted)
    .reduce((sum, n) => sum + (n.comments?.filter(c => c.softDeleted).length || 0), 0)
})
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
