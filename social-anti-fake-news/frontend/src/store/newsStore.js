// frontend/src/store/newsStore.js
import { defineStore } from 'pinia'
import newsData from '../data/mockNews.json' // keep your mock data file

export const useNewsStore = defineStore('news', {
  state: () => ({
    newsList: newsData.map(n => ({ ...n, softDeleted:false })), // add softDeleted flag
    selectedNews: null,
    filter: 'all', // all | fake | notFake | undecided | removed
    searchQuery: '',
    perPage: 6,
    currentPage: 1
  }),
  getters: {
    filteredNews(state){
      let list = state.newsList
      // hide soft-deleted items by default (unless filter 'removed' requested)
      if(state.filter !== 'removed') list = list.filter(n => !n.softDeleted)
      // status filter
      if(state.filter === 'fake') list = list.filter(n => n.status === 'fake')
      if(state.filter === 'notFake') list = list.filter(n => n.status === 'notFake')
      if(state.filter === 'undecided') list = list.filter(n => n.status === 'undecided')
      if(state.searchQuery){
        const q = state.searchQuery.toLowerCase()
        list = list.filter(n => (n.title+' '+(n.shortDetail||'')+' '+(n.detail||'')).toLowerCase().includes(q))
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

    addNews(news){
      // news: { title, shortDetail, detail, image, reporterId, reporterName }
      const id = Date.now()
      const entry = {
        id,
        title: news.title,
        shortDetail: news.shortDetail,
        detail: news.detail,
        image: news.image || '',
        reporter: news.reporterName || 'anonymous',
        reporterId: news.reporterId || null,
        createdAt: new Date().toISOString(),
        votes: { fake:0, notFake:0 },
        status: 'undecided',
        comments: [],
        softDeleted: false
      }
      this.newsList.unshift(entry)
    },

    vote(newsId, type, userId){
      // Readers can only vote; caller should check permission
      const n = this.newsList.find(x => x.id == newsId)
      if(!n) return
      n.votes = n.votes || { fake:0, notFake:0 }
      if(type === 'fake') n.votes.fake = (n.votes.fake||0) + 1
      if(type === 'notFake') n.votes.notFake = (n.votes.notFake||0) + 1
      // recompute status
      if((n.votes.fake||0) > (n.votes.notFake||0)) n.status = 'fake'
      else if((n.votes.notFake||0) > (n.votes.fake||0)) n.status = 'notFake'
      else n.status = 'undecided'
      if(this.selectedNews?.id === n.id) this.selectedNews = {...n}
    },

    addComment(newsId, authorName, text){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return
      n.comments = n.comments || []
      n.comments.push({ id: Date.now(), author: authorName, text, createdAt: new Date().toISOString(), softDeleted:false })
    },

    adminSoftDeleteNews(newsId){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return false
      n.softDeleted = true
      return true
    },

    adminRestoreNews(newsId){
      const n = this.newsList.find(x => x.id == newsId); if(!n) return false
      n.softDeleted = false
      return true
    },

    adminRemoveComment(newsId, commentId){ // soft delete comment
      const n = this.newsList.find(x => x.id == newsId); if(!n || !n.comments) return false
      const c = n.comments.find(x => x.id == commentId); if(!c) return false
      c.softDeleted = true
      // Optionally recalc score (example: remove comment may affect votes - minimal example)
      // Here we don't change votes automatically, but could implement custom logic if needed.
      return true
    },

    adminHardDeleteComment(newsId, commentId){
      const n = this.newsList.find(x => x.id == newsId); if(!n || !n.comments) return false
      n.comments = n.comments.filter(x => x.id != commentId)
      return true
    }
  }
})

