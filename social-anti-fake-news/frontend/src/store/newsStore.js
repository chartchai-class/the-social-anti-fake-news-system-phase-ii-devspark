import { defineStore } from 'pinia'
import newsData from '../data/mockNews.json'

export const useNewsStore = defineStore('news', {
  state: () => ({
    newsList: newsData,
    selectedNews: null
  }),
  actions: {
    setSelectedNews(id) {
      this.selectedNews = this.newsList.find(n => n.id === id)
    },
    addComment(newsId, comment) {
      const news = this.newsList.find(n => n.id === newsId)
      if (!news.comments) news.comments = []
      news.comments.push(comment)

      if (this.selectedNews?.id === newsId) {
        this.selectedNews = { ...news }
      }
    },
    removeComment(newsId, commentIndex) {
      const news = this.newsList.find(n => n.id === newsId)
      if (!news) return
      if (!news.comments) return
      news.comments.splice(commentIndex, 1)
      if (this.selectedNews?.id === newsId) this.selectedNews = { ...news }
    },
    removeNews(newsId) {
      const idx = this.newsList.findIndex(n => n.id === newsId)
      if (idx !== -1) this.newsList.splice(idx, 1)
      if (this.selectedNews?.id === newsId) this.selectedNews = null
    },
    vote(newsId, type) {
  const news = this.newsList.find(n => n.id === newsId)
  if (!news) return

  if (!news.votes) news.votes = { fake: 0, notFake: 0 }

  if (type === 'fake') news.votes.fake++
  else news.votes.notFake++

  if (news.votes.fake > news.votes.notFake) {
    news.status = 'fake'
  } else if (news.votes.notFake > news.votes.fake) {
    news.status = 'notFake'
  } else {
    news.status = 'undecided'
  }

  if (this.selectedNews?.id === newsId) {
    this.selectedNews = { ...news }
  }
},

  }
})
