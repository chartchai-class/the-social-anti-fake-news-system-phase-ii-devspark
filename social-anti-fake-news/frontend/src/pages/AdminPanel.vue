<template>
  <div class="container">
    <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:1rem">
      <h2>Admin Panel</h2>
      <div>
        <button class="btn btn-ghost" @click="refresh">Refresh</button>
      </div>
    </div>

    <div class="card" style="margin-bottom:1rem">
      <h3>Users</h3>
      <table style="width:100%">
        <thead><tr><th>Username</th><th>Email</th><th>Role</th><th></th></tr></thead>
        <tbody>
        <tr v-for="u in users" :key="u.id" style="border-top:1px solid #f0f3f8">
          <td style="padding:.5rem">{{u.username}}</td>
          <td>{{u.email}}</td>
          <td>{{u.role}}</td>
          <td style="text-align:right">
            <button v-if="u.role!=='ADMIN'" class="btn btn-primary" @click="upgrade(u.id)">Make Admin</button>
            <button v-else class="btn btn-ghost" @click="downgrade(u.id)">Revoke</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="card" style="margin-bottom:1rem">
      <h3>All News (including removed)</h3>
      <div v-for="n in allNews" :key="n.id" class="card" style="margin-bottom:.6rem;display:flex;align-items:center;gap:.6rem">
        <img :src="n.image||'/images/image1.jpg'" style="width:80px;height:60px;object-fit:cover;border-radius:6px" />
        <div style="flex:1">
          <div style="font-weight:600">{{n.title}} <span v-if="n.softDeleted" style="color:#f43f5e;font-size:.9rem">[removed]</span></div>
          <div class="kv">{{n.reporter}} • {{formatDate(n.createdAt)}}</div>
        </div>
        <div style="display:flex;gap:.4rem">
          <button class="btn btn-ghost" @click="toggleRemove(n)">{{ n.softDeleted ? 'Restore' : 'Remove' }}</button>
          <router-link :to="`/news/${n.id}`" class="btn">Open</router-link>
        </div>
      </div>
    </div>

    <div class="card">
      <h3>Comments (removed)</h3>
      <div v-for="n in newsWithRemovedComments" :key="n.id" class="card" style="margin-bottom:.6rem">
        <div style="font-weight:600">{{n.title}}</div>
        <div v-for="c in n.comments.filter(x => x.softDeleted)" :key="c.id" style="border-top:1px dashed #e6eef8;padding:.6rem">
          <div class="kv"><strong>{{c.author}}</strong> • {{formatDate(c.createdAt)}}</div>
          <p style="margin-top:.4rem">{{c.text}}</p>
          <div style="display:flex;gap:.4rem;justify-content:flex-end">
            <button class="btn btn-ghost" @click="restoreComment(n.id, c.id)">Restore</button>
            <button class="btn" @click="hardDeleteComment(n.id, c.id)">Delete</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { useAuthStore } from '../store/authStore'
import { useNewsStore } from '../store/newsStore'
import { computed } from 'vue'

export default {
  setup(){
    const auth = useAuthStore()
    const ns = useNewsStore()
    const users = computed(()=> auth.users)
    const allNews = computed(()=> ns.newsList)
    const newsWithRemovedComments = computed(()=> ns.newsList.filter(n => (n.comments||[]).some(c => c.softDeleted)))

    function refresh(){ alert('Refreshed (mock).') }

    function upgrade(id){ if(!confirm('Make admin?')) return; auth.upgradeUserToAdmin(id) }
    function downgrade(id){ if(!confirm('Revoke admin?')) return; auth.downgradeUserToReader(id) }

    function toggleRemove(n){
      if(n.softDeleted) ns.adminRestoreNews(n.id)
      else {
        if(!confirm('Remove this news? it will be hidden from users')) return
        ns.adminSoftDeleteNews(n.id)
      }
    }

    function restoreComment(newsId, cid){ // un-soft-delete comment
      const news = ns.newsList.find(x=>x.id==newsId)
      const c = news.comments.find(x=>x.id==cid); if(!c) return
      c.softDeleted = false
    }

    function hardDeleteComment(newsId, cid){
      if(!confirm('Permanently delete this comment?')) return
      ns.adminHardDeleteComment(newsId, cid)
    }

    function formatDate(d){ try{return new Date(d).toLocaleString()}catch(e){return d} }

    return { users, allNews, newsWithRemovedComments, refresh, upgrade, downgrade, toggleRemove, restoreComment, hardDeleteComment, formatDate }
  }
}
</script>