import { defineStore } from 'pinia'
import newsData from '../data/mockNews.json'

export const useNewsStore = defineStore('news', {
  state: () => ({
    newsList: newsData.map(n => ({...n})),
    selectedNews: null,
    filter: 'all',
    searchQuery: '',
    perPage: 6,
    currentPage: 1,
    user: { id: null, name: null, role: 'USER', isAdmin:false },
    // small mock users for admin testing
    mockUsers: []
  }),
  getters: {
    filteredNews(state){
      let list = state.newsList
      if (state.filter === 'fake') list = list.filter(n => n.status==='fake')
      if (state.filter === 'notFake') list = list.filter(n => n.status==='notFake')
      if (state.searchQuery){
        const q = state.searchQuery.toLowerCase()
        list = list.filter(n => (n.title + ' ' + (n.shortDetail||'') + ' ' + (n.detail||'')).toLowerCase().includes(q))
      }
      return list
    },
    pagedNews(state){ const start=(state.currentPage-1)*state.perPage; return this.filteredNews.slice(start,start+state.perPage) },
    totalPages(state){ return Math.max(1, Math.ceil(this.filteredNews.length/state.perPage)) }
  },
  actions: {
    setFilter(f){ this.filter = f; this.currentPage = 1 },
    setSearch(q){ this.searchQuery = q; this.currentPage = 1 },
    setPerPage(n){ this.perPage = Number(n); this.currentPage = 1 },
    goPage(n){ this.currentPage = Math.max(1, Math.min(n, this.totalPages)) },
    setSelectedNews(id){ this.selectedNews = this.newsList.find(n => n.id==id) || null },

    vote(newsId, type){
      const news = this.newsList.find(n=>n.id==newsId); if(!news) return
      news.votes = news.votes || { fake:0, notFake:0 }
      if(type==='fake') news.votes.fake = (news.votes.fake||0)+1
      if(type==='notFake') news.votes.notFake = (news.votes.notFake||0)+1
      if((news.votes.fake||0) > (news.votes.notFake||0)) news.status = 'fake'
      else if((news.votes.notFake||0) > (news.votes.fake||0)) news.status = 'notFake'
      else news.status = 'undecided'
      if(this.selectedNews?.id === newsId) this.selectedNews = {...news}
    },

    addComment(newsId, text){
      const news = this.newsList.find(n=>n.id==newsId); if(!news) return
      news.comments = news.comments || []
      news.comments.push({ id: Date.now(), text, author: this.user.name || 'anonymous', createdAt: new Date().toISOString() })
      if(this.selectedNews?.id == newsId) this.selectedNews = {...news}
    },

    adminRemoveNews(newsId){
      if(!this.user.isAdmin) return false
      this.newsList = this.newsList.filter(n => n.id !== newsId)
      if(this.selectedNews?.id === newsId) this.selectedNews = null
      return true
    },

    adminRemoveComment(newsId, commentId){
      if(!this.user.isAdmin) return false
      const news = this.newsList.find(n=>n.id==newsId); if(!news) return false
      news.comments = (news.comments||[]).filter(c => c.id !== commentId)
      if(this.selectedNews?.id==newsId) this.selectedNews = {...news}
      return true
    },

    // user helpers (for admin testing)
    setUserRole(userId, role){
      const u = this.mockUsers.find(x => x.id == userId)
      if(!u) return false
      u.role = role
      u.isAdmin = role === 'ADMIN'
      return true
    }
  }
})

