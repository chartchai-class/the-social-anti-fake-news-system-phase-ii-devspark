import { defineStore } from 'pinia'

const STORAGE_KEY = 'afn_auth_v1'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: JSON.parse(localStorage.getItem(STORAGE_KEY) || 'null'), // { id, username, name, email, role, imageUrl, isAdmin }
        users: JSON.parse(localStorage.getItem('afn_users_v1') || '[]') // local mock user list for admin features
    }),
    getters: {
        isLoggedIn: (state) => !!state.user,
        isAdmin: (state) => !!state.user && state.user.role === 'ADMIN',
        isMember: (state) => !!state.user && state.user.role === 'MEMBER',
        isReader: (state) => !!state.user && state.user.role === 'READER'
    },
    actions: {
        saveState() {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(this.user))
            localStorage.setItem('afn_users_v1', JSON.stringify(this.users))
        },
        registerClient(userObj) {
            // userObj: { username, email, password (not stored), name, imageUrl }
            const id = Date.now()
            const base = {
                id,
                username: userObj.username,
                email: userObj.email,
                name: userObj.name || userObj.username,
                imageUrl: userObj.imageUrl || '',
                role: 'READER', // default role
                isActive: true
            }
            this.users.push(base)
            this.user = base
            this.saveState()
            return base
        },
        setUser(user) {
            this.user = user
            this.saveState()
        },
        logout() {
            this.user = null
            this.saveState()
        },
        findUserById(id) {
            return this.users.find(u => u.id === Number(id)) || null
        },
        upgradeUserToMember(id) {
            const u = this.users.find(x => x.id === Number(id))
            if (!u) return null
            u.role = 'MEMBER'
            this.saveState()
            return u
        },
        upgradeUserToAdmin(id) {
            const u = this.users.find(x => x.id === Number(id))
            if (!u) return null
            u.role = 'ADMIN'; u.isAdmin = true
            this.saveState()
            return u
        },
        downgradeUserToReader(id) {
            const u = this.users.find(x => x.id === Number(id))
            if (!u) return null
            u.role = 'READER'; u.isAdmin = false
            this.saveState()
            return u
        },
        addMockUsersIfEmpty(){
            if(this.users.length === 0){
                this.users = [
                    { id: 1, username: 'admin', name: 'Admin', email: 'admin@local', role:'ADMIN', imageUrl:'', isAdmin:true},
                    { id: 2, username: 'member1', name: 'Member One', email:'member1@local', role:'MEMBER', imageUrl:''},
                    { id: 3, username: 'reader1', name: 'Reader One', email:'reader1@local', role:'READER', imageUrl:''},
                ]
                this.saveState()
            }
        }
    }
})