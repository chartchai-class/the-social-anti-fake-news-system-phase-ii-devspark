<template>
  <div class="min-h-screen bg-gray-50 flex items-center">
    <div class="max-w-md mx-auto w-full px-4">
      <div class="bg-white rounded-2xl shadow-xl border border-gray-100 p-6">
        <h1 class="text-2xl font-bold mb-2">Sign in</h1>
        <p class="text-sm text-gray-600 mb-6">Use a demo user to authenticate and see role-based features.</p>

        <div class="space-y-3 mb-6">
          <button class="w-full px-4 py-2 rounded-lg bg-blue-600 hover:bg-blue-700 text-white font-semibold" @click="loginAs('admin')">Login as Admin</button>
          <button class="w-full px-4 py-2 rounded-lg bg-green-600 hover:bg-green-700 text-white font-semibold" @click="loginAs('member1')">Login as Member</button>
          <button class="w-full px-4 py-2 rounded-lg bg-gray-800 hover:bg-black text-white font-semibold" @click="loginAs('reader1')">Login as Reader</button>
        </div>

        <div class="border-t pt-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">Or username</label>
          <input v-model="username" type="text" placeholder="admin | member1 | reader1" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 mb-3" />
          <button class="w-full px-4 py-2 rounded-lg bg-primary text-white font-semibold hover:bg-primary-dark" @click="login">Login</button>
        </div>

        <p class="text-sm text-gray-600 mt-4">No account? <router-link to="/register" class="text-blue-600 hover:underline">Register</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/authStore'

const router = useRouter()
const auth = useAuthStore()
const username = ref('')

function login(){
  if(!username.value.trim()) return
  const u = auth.loginLocal(username.value.trim())
  if(!u) return alert('User not found. Try admin, member1, reader1')
  router.push('/')
}

function loginAs(name){
  username.value = name
  login()
}
</script>
