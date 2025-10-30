import { defineStore } from 'pinia'

const STORAGE_KEY = 'afn_auth_v1'
const USERS_KEY = 'afn_users_v1'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: JSON.parse(localStorage.getItem(STORAGE_KEY) || 'null'),
        users: JSON.parse(localStorage.getItem(USERS_KEY) || '[]')
    }),
    getters: {
        isLoggedIn: (s) => !!s.user,
        isAdmin: (s) => s.user?.role === 'ADMIN',
        isMember: (s) => s.user?.role === 'MEMBER',
        isReader: (s) => s.user?.role === 'READER'
    },
    actions: {
        save() {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(this.user))
            localStorage.setItem(USERS_KEY, JSON.stringify(this.users))
        },
        addMockUsersIfEmpty(){
            if(this.users.length===0){
                this.users = [
                    { id: 1, username:'admin', email:'admin@local', name:'Admin', role:'ADMIN', imageUrl:'' },
                    { id: 2, username:'member1', email:'m1@local', name:'Member One', role:'MEMBER', imageUrl:'' },
                    { id: 3, username:'reader1', email:'r1@local', name:'Reader One', role:'READER', imageUrl:'' }
                ]
                this.save()
            }
        },
        registerClient(payload){
            // payload: {username,email,name,imageUrl}
            const id = Date.now()
            const user = { id, username: payload.username, email: payload.email, name: payload.name||payload.username, imageUrl: payload.imageUrl||'', role:'READER' }
            this.users.push(user)
            this.user = user
            this.save()
            return user
        },
        loginLocal(username){
            const u = this.users.find(x => x.username === username) || null
            if(u){
                this.user = u
                this.save()
            }
            return u
        },
        logout(){
            this.user = null
            this.save()
        },
        upgradeToMember(id){
            const u = this.users.find(x => x.id === Number(id)); if(!u) return null
            u.role = 'MEMBER'; this.save(); return u
        },
        upgradeToAdmin(id){
            const u = this.users.find(x => x.id === Number(id)); if(!u) return null
            u.role = 'ADMIN'; this.save(); return u
        },
        downgradeToReader(id){
            const u = this.users.find(x => x.id === Number(id)); if(!u) return null
            u.role = 'READER'; this.save(); return u
        },
        findUserById(id){ return this.users.find(x => x.id == id) || null }
    }
})
