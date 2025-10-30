<template>
  <header class="bg-white/80 backdrop-blur border-b border-gray-100">
    <div class="max-w-6xl mx-auto px-4" style="display:flex;align-items:center;justify-content:space-between">
      <div class="text-lg font-bold">📰 Anti-Fake News</div>
      <div style="display:flex;align-items:center;gap:.6rem">
        <slot name="search"></slot>
        <router-link class="px-3 py-2 rounded-full bg-gray-100 hover:bg-gray-200 text-gray-700 border border-gray-300" to="/add">Add News</router-link>
        <router-link class="px-3 py-2 rounded-full bg-gray-100 hover:bg-gray-200 text-gray-700 border border-gray-300" to="/register">Register</router-link>

        <div v-if="auth.isLoggedIn" style="display:flex;align-items:center;gap:.5rem">
          <img :src="auth.user.imageUrl || '/images/avatar-default.png'" style="width:36px;height:36px;border-radius:50%;object-fit:cover" />
          <router-link :to="`/user/${auth.user.id}`" class="kv">{{ auth.user.name }}</router-link>
          <button class="px-3 py-2 rounded-full bg-gray-100 hover:bg-gray-200 text-gray-700 border border-gray-300" @click="logout">Logout</button>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { useAuthStore } from '../store/authStore'
import { useRouter } from 'vue-router'
export default {
  setup(){
    const auth = useAuthStore()
    const router = useRouter()
    function logout(){ auth.logout(); router.push('/') }
    return { auth, logout }
  }
}
</script>
