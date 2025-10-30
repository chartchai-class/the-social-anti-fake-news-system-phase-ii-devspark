import { defineStore } from 'pinia'
import mock from '../data/mockNews.json' // ensure you have this file or replace with empty array

export const useNewsStore = defineStore('news', {
  state: () => ({
    newsList: (mock || []).map(n => ({...n, softDeleted:false})),
    selectedNews: null,
    filter: 'all', // all | fake | notFake | undecided | removed
    searchQuery: '',
    perPage: 6,
    currentPage: 1
  }),
  getters: {
    filteredNews(state){
      let list = state.newsList
      if(state.filter !== 'removed') list = list.filter(n => !n.softDeleted)
      if(state.filter === 'fake') list = list.filter(n => n.status === 'fake')
      if(state.filter === 'notFake') list = list.filter(n => n.status === 'notFake')
      if(state.filter === 'undecided') list = list.filter(n => n.status === 'undecided')
      if(state.searchQuery){
        const q = state.searchQuery.toLowerCase()
        list = list.filter(n => (n.title + ' ' + (n.shortDetail||'') + ' ' + (n.detail||'')).toLowerCase().includes(q))
      }
      return list
    },
    pagedNews(state){
      const start = (state.currentPage - 1) * state.perPage
      return this.filteredNews.slice(start, start + state.perPage)
    },
    totalPages(state){ return Math.max(1, Math.ceil(this.filteredNews.length/state.perPage)) }
  },
  actions: {
    setFilter(f){ this.filter = f; this.currentPage = 1 },
    setSearch(q){ this.searchQuery = q; this.currentPage = 1 },
    setPerPage(n){ this.perPage = Number(n); this.currentPage = 1 },
    goPage(n){ this.currentPage = Math.max(1, Math.min(n, this.totalPages)) },
    setSelectedNews(id){ this.selectedNews = this.newsList.find(x => x.id == id) || null },

    addNews(payload){
      const id = Date.now()
      const entry = {
        id,
        title: payload.title,
        shortDetail: payload.shortDetail,
        detail: payload.detail,
        image: payload.image || '',
        reporter: payload.reporterName || 'anonymous',
        reporterId: payload.reporterId || null,
        createdAt: new Date().toISOString(),
        votes: { fake:0, notFake:0 },
        status: 'undecided',
        comments: []
      }
      this.newsList.unshift(entry)
      return entry
    },

    vote(newsId, type){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return
      n.votes = n.votes || { fake:0, notFake:0 }
      if(type === 'fake') n.votes.fake = (n.votes.fake||0) + 1
      if(type === 'notFake') n.votes.notFake = (n.votes.notFake||0) + 1
      if((n.votes.fake||0) > (n.votes.notFake||0)) n.status = 'fake'
      else if((n.votes.notFake||0) > (n.votes.fake||0)) n.status = 'notFake'
      else n.status = 'undecided'
      if(this.selectedNews?.id == n.id) this.selectedNews = {...n}
    },

    addComment(newsId, authorName, text){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return
      n.comments = n.comments || []
      n.comments.push({ id: Date.now(), author: authorName, text, createdAt: new Date().toISOString(), softDeleted:false })
    },

    adminSoftDeleteNews(newsId){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return false
      n.softDeleted = true; return true
    },
    adminRestoreNews(newsId){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return false
      n.softDeleted = false; return true
    },

    adminSoftDeleteComment(newsId, commentId){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return false
      const c = n.comments.find(x => x.id == commentId); if(!c) return false
      c.softDeleted = true; return true
    },
    adminHardDeleteComment(newsId, commentId){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return false
      n.comments = n.comments.filter(x => x.id != commentId); return true
    }
  }
})

