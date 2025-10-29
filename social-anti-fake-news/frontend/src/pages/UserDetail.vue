<!-- frontend/src/pages/UserDetail.vue -->
<template>
  <div class="container">
    <div class="card" style="max-width:720px;margin:1rem auto">
      <div style="display:flex;gap:1rem;align-items:center">
        <img :src="user.imageUrl || '/images/avatar-default.png'" style="width:120px;height:120px;border-radius:12px;object-fit:cover" />
        <div>
          <h2>{{ user.name }}</h2>
          <div class="kv">Username: {{ user.username }}</div>
          <div class="kv">Email: {{ user.email }}</div>
          <div class="kv">Role: {{ user.role }}</div>
          <div style="margin-top:.6rem">
            <button v-if="canEdit" class="btn btn-primary" @click="becomeMember">Upgrade to Member (demo)</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '../store/authStore'
import { useRoute } from 'vue-router'
import { computed } from 'vue'

export default {
  setup(){
    const auth = useAuthStore()
    const route = useRoute()
    const userId = route.params.id
    const user = computed(() => auth.findUserById(userId) || (auth.user && auth.user.id == userId ? auth.user : null) )

    function becomeMember(){
      // This is a demo action: in real app call backend endpoint for payment/approval
      if(confirm('Make this user a MEMBER? (demo)')){
        auth.upgradeUserToMember(user.value.id)
        alert('Upgraded to MEMBER (mock).')
      }
    }

    const canEdit = true // you can condition this to admin only or owner

    return { user, canEdit, becomeMember }
  }
}
</script>
