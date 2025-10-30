<template>
  <header class="header">
    <div class="container" style="display:flex;align-items:center;justify-content:space-between">
      <div class="brand">📰 Anti-Fake News</div>
      <div style="display:flex;align-items:center;gap:.6rem">
        <slot name="search"></slot>
        <router-link class="btn btn-ghost" to="/add">Add News</router-link>
        <router-link class="btn btn-ghost" to="/register">Register</router-link>

        <div v-if="auth.isLoggedIn" style="display:flex;align-items:center;gap:.5rem">
          <img :src="auth.user.imageUrl || '/images/avatar-default.png'" style="width:36px;height:36px;border-radius:50%;object-fit:cover" />
          <router-link :to="`/user/${auth.user.id}`" class="kv">{{ auth.user.name }}</router-link>
          <button class="btn btn-ghost" @click="logout">Logout</button>
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
