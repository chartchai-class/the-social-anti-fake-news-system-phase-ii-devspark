<template>
  <div class="container">
    <div style="display:flex;gap:1rem;align-items:center;justify-content:space-between;margin-bottom:1rem">
      <h2>Admin Panel</h2>
      <div>
        <button class="btn btn-ghost" @click="refresh">Refresh</button>
      </div>
    </div>

    <div class="card" style="margin-bottom:1rem">
      <h3>Users</h3>
      <table style="width:100%;border-collapse:collapse">
        <thead style="text-align:left;color:var(--text-secondary)">
        <tr><th style="padding:.5rem">Username</th><th>Email</th><th>Role</th><th></th></tr>
        </thead>
        <tbody>
        <tr v-for="u in users" :key="u.id" style="border-top:1px solid #f0f3f8">
          <td style="padding:.5rem">{{u.username||u.name}}</td>
          <td>{{u.email||'-'}}</td>
          <td>{{u.role||(u.isAdmin? 'ADMIN':'USER')}}</td>
          <td style="text-align:right">
            <button v-if="u.role!=='ADMIN' && !u.isAdmin" class="btn btn-primary" @click="upgrade(u.id)">Make Admin</button>
            <button v-else class="btn btn-ghost" @click="downgrade(u.id)">Revoke</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="card">
      <h3>News (manage)</h3>
      <div v-for="n in newsList" :key="n.id" class="card" style="margin-bottom:.6rem">
        <div style="display:flex;gap:.6rem;align-items:center">
          <img :src="n.image||'/images/image1.jpg'" style="width:64px;height:48px;object-fit:cover;border-radius:6px" />
          <div style="flex:1">
            <div style="font-weight:600">{{n.title}}</div>
            <div class="kv">{{n.reporter}} • {{formatDate(n.createdAt)}}</div>
          </div>
          <div style="display:flex;gap:.4rem">
            <button class="btn btn-ghost" @click="removeNews(n.id)">Remove</button>
            <router-link :to="`/news/${n.id}`" class="btn">Open</router-link>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { useNewsStore } from '../store/newsStore'
import { onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

export default {
  setup(){
    const ns = useNewsStore()
    const router = useRouter()
    const users = computed(()=> ns.mockUsers)
    const newsList = computed(()=> ns.newsList)

    function refresh() {
      // If backend has GET /users, call it. Here we rely on mockUsers in store.
      alert('Refreshed (mock)')
    }

    function upgrade(id){
      if(!confirm('Make this user an ADMIN?')) return
      ns.setUserRole(id, 'ADMIN')
      alert('Upgraded (mock). Replace with backend call if available.')
    }
    function downgrade(id){
      if(!confirm('Revoke admin?')) return
      ns.setUserRole(id, 'USER')
    }

    function removeNews(id){
      if(!confirm('Remove this news?')) return
      // if backend exists, call DELETE /api/news/{id}
      ns.adminRemoveNews(id)
    }

    function formatDate(d){ try{return new Date(d).toLocaleString()}catch(e){return d} }

    onMounted(()=> {
      // ensure admin flag on current user for UI testing
      ns.user = ns.user || { id: 1, name: 'Admin (mock)', isAdmin: true, role:'ADMIN' }
      if(!ns.mockUsers.length){
        ns.mockUsers = [
          { id:1, username:'admin',email:'admin@local',name:'Admin',role:'ADMIN', isAdmin:true},
          { id:2, username:'bob',email:'bob@local',name:'Bob',role:'USER', isAdmin:false},
        ]
      }
    })

    return { users, newsList, refresh, upgrade, downgrade, removeNews, formatDate }
  }
}
</script>
