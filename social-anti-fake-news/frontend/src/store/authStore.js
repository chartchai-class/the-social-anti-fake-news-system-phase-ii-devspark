import { defineStore } from 'pinia'
import { supabase, userService } from '../services/supabase'

const AUTH_KEY = 'afn_auth_v1'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: JSON.parse(localStorage.getItem(AUTH_KEY) || 'null'),
        users: [],
        session: null
    }),
    getters: {
        isLoggedIn: (s) => !!s.user && !!s.session,
        isAdmin: (s) => s.user?.role === 'ADMIN',
        isMember: (s) => s.user?.role === 'MEMBER',
        isReader: (s) => s.user?.role === 'READER'
    },
    actions: {
        save() {
            if (this.user) {
                localStorage.setItem(AUTH_KEY, JSON.stringify(this.user))
            } else {
                localStorage.removeItem(AUTH_KEY)
            }
        },
        
        async initialize() {
            // Check for existing session
            const { data: { session } } = await supabase.auth.getSession()
            if (session) {
                this.session = session
                await this.loadUserProfile(session.user.id)
            }
            
            // Listen for auth changes
            supabase.auth.onAuthStateChange(async (event, session) => {
                this.session = session
                if (session) {
                    await this.loadUserProfile(session.user.id)
                } else {
                    this.user = null
                    this.save()
                }
            })
        },

        async loadUserProfile(userId) {
            try {
                const profile = await userService.getProfile(userId)
                this.user = {
                    id: profile.id,
                    username: profile.username,
                    email: profile.email,
                    name: profile.full_name || `${profile.first_name} ${profile.last_name}`,
                    firstName: profile.first_name,
                    lastName: profile.last_name,
                    imageUrl: profile.image_url || '',
                    role: profile.role || 'READER'
                }
                this.save()
            } catch (error) {
                console.error('Error loading user profile:', error)
            }
        },

        async register(payload) {
            try {
                // Register user with Supabase Auth
                const result = await userService.register({
                    username: payload.username,
                    email: payload.email,
                    password: payload.password,
                    name: payload.name,
                    surname: payload.surname,
                    imageUrl: payload.imageUrl || ''
                })
                
                if (result.user) {
                    // Set session
                    this.session = result.session
                    // If session exists (auto-confirm), load/create profile now
                    if (this.session) {
                        await this.loadUserProfile(result.user.id)
                        return { success: true, user: this.user }
                    }
                    // If session is null (email confirmation required), return success and
                    // let the user confirm email then log in; profile will be created on first login
                    return { success: true, user: null }
                }
                throw new Error('Registration failed')
            } catch (error) {
                console.error('Registration error:', error)
                throw error
            }
        },

        async login(email, password) {
            try {
                const result = await userService.login(email, password)
                this.session = result.session
                if (result.profile) {
                    this.user = {
                        id: result.profile.id,
                        username: result.profile.username,
                        email: result.profile.email,
                        name: result.profile.full_name || `${result.profile.first_name || ''} ${result.profile.last_name || ''}`.trim(),
                        firstName: result.profile.first_name,
                        lastName: result.profile.last_name,
                        imageUrl: result.profile.image_url || '',
                        role: result.profile.role || 'READER'
                    }
                    this.save()
                    return { success: true, user: this.user }
                }
                throw new Error('Profile not found')
            } catch (error) {
                console.error('Login error:', error)
                throw error
            }
        },

        async logout() {
            try {
                await supabase.auth.signOut()
                this.user = null
                this.session = null
                this.save()
            } catch (error) {
                console.error('Logout error:', error)
                throw error
            }
        },

        // dev test logins removed

        async loadAllUsers() {
            try {
                const users = await userService.getAllUsers()
                this.users = users.map(u => ({
                    id: u.id,
                    username: u.username,
                    email: u.email,
                    name: u.full_name || `${u.first_name} ${u.last_name}`,
                    imageUrl: u.image_url || '',
                    role: u.role || 'READER'
                }))
                return this.users
            } catch (error) {
                console.error('Error loading users:', error)
                throw error
            }
        },

        async upgradeToMember(id) {
            try {
                const updated = await userService.updateRole(id, 'MEMBER')
                await this.loadAllUsers()
                if (this.user?.id === id) {
                    await this.loadUserProfile(id)
                }
                return updated
            } catch (error) {
                console.error('Error upgrading user:', error)
                throw error
            }
        },

        async upgradeToAdmin(id) {
            try {
                const updated = await userService.updateRole(id, 'ADMIN')
                await this.loadAllUsers()
                if (this.user?.id === id) {
                    await this.loadUserProfile(id)
                }
                return updated
            } catch (error) {
                console.error('Error upgrading user:', error)
                throw error
            }
        },

        async downgradeToReader(id) {
            try {
                const updated = await userService.updateRole(id, 'READER')
                await this.loadAllUsers()
                if (this.user?.id === id) {
                    await this.loadUserProfile(id)
                }
                return updated
            } catch (error) {
                console.error('Error downgrading user:', error)
                throw error
            }
        },

        upgradeUserToMember(id) { return this.upgradeToMember(id) },
        upgradeUserToAdmin(id) { return this.upgradeToAdmin(id) },
        
        async findUserById(id) {
            try {
                const profile = await userService.getProfile(id)
                return {
                    id: profile.id,
                    username: profile.username,
                    email: profile.email,
                    name: profile.full_name || `${profile.first_name} ${profile.last_name}`,
                    imageUrl: profile.image_url || '',
                    role: profile.role || 'READER'
                }
            } catch (error) {
                console.error('Error finding user:', error)
                return null
            }
        }
    }
})
