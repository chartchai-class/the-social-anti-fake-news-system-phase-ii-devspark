import { createClient } from '@supabase/supabase-js'

// Supabase configuration
const supabaseUrl = 'https://sdhltyzwejuormbcfskd.supabase.co'
const supabaseAnonKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNkaGx0eXp3ZWp1b3JtYmNmc2tkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjE4ODk1MzcsImV4cCI6MjA3NzQ2NTUzN30.yzIcs33FUbpEQEV1Nb3bFTu3ko_y6O9Ox7DeEZPbNdE'

export const supabase = createClient(supabaseUrl, supabaseAnonKey)

// Database service functions
export const userService = {
  // Register new user
        async register(userData) {
            // First, sign up the user with Supabase Auth
            const { data: authData, error: authError } = await supabase.auth.signUp({
                email: (userData.email || '').toLowerCase().trim(),
                password: userData.password,
                options: {
                    data: {
                        username: userData.username,
                        first_name: userData.name,
                        last_name: userData.surname,
                        full_name: `${userData.name} ${userData.surname}`,
                        image_url: userData.imageUrl || null
                    }
                }
            })
            
            if (authError) throw authError
            
            if (authData.user && authData.session) {
                
                await new Promise(resolve => setTimeout(resolve, 500))
                
                const { error: profileError } = await supabase
                    .from('profiles')
                    .update({
                        username: userData.username,
                        first_name: userData.name,
                        last_name: userData.surname,
                        full_name: `${userData.name} ${userData.surname}`,
                        image_url: userData.imageUrl || null,
                        role: 'READER'
                    })
                    .eq('id', authData.user.id)
                
                if (profileError) {
                    // If update fails, try insert (in case trigger didn't fire)
                    const { error: insertError } = await supabase
                        .from('profiles')
                        .insert({
                            id: authData.user.id,
                            username: userData.username,
                            email: userData.email,
                            first_name: userData.name,
                            last_name: userData.surname,
                            full_name: `${userData.name} ${userData.surname}`,
                            image_url: userData.imageUrl || null,
                            role: 'READER'
                        })
                        .select()
                        .single()
                    
                    if (insertError) throw insertError
                }
            }
            
            return authData
        },

        // Login user
        async login(email, password) {
            const { data: authData, error: authError } = await supabase.auth.signInWithPassword({
                email,
                password
            })
            
            if (authError) throw authError
            
            // Get user profile (create if missing)
            let { data: profile } = await supabase
                .from('profiles')
                .select('*')
                .eq('id', authData.user.id)
                .maybeSingle()
            
            if (!profile) {
                const meta = authData.user.user_metadata || {}
                const payload = {
                  id: authData.user.id,
                  username: meta.username || null,
                  email: authData.user.email || null,
                  first_name: meta.first_name || null,
                  last_name: meta.last_name || null,
                  full_name: meta.full_name || [meta.first_name, meta.last_name].filter(Boolean).join(' ') || null,
                  image_url: meta.image_url || null,
                  role: 'READER'
                }
                const { data: created, error: cErr } = await supabase
                  .from('profiles')
                  .insert(payload)
                  .select()
                  .maybeSingle()
                if (cErr) throw cErr
                profile = created
            }
            
            return { 
                user: authData.user, 
                session: authData.session,
                profile 
            }
        },

  // Get user profile
  async getProfile(userId) {
    const { data, error } = await supabase
      .from('profiles')
      .select('*')
      .eq('id', userId)
      .maybeSingle()
    
    if (!data) {
      const { data: authData } = await supabase.auth.getUser()
      const meta = authData?.user?.user_metadata || {}
      const payload = {
        id: userId,
        username: meta.username || null,
        email: authData?.user?.email || null,
        first_name: meta.first_name || null,
        last_name: meta.last_name || null,
        full_name: meta.full_name || [meta.first_name, meta.last_name].filter(Boolean).join(' ') || null,
        image_url: meta.image_url || null,
        role: 'READER'
      }
      const { data: inserted, error: insertErr } = await supabase
        .from('profiles')
        .insert(payload)
        .select()
        .maybeSingle()
      if (insertErr) throw insertErr
      return inserted
    }
    if (error) throw error
    return data
  },

  // Update user profile
  async updateProfile(userId, updates) {
    const { data, error } = await supabase
      .from('profiles')
      .update(updates)
      .eq('id', userId)
      .select()
      .single()
    
    if (error) throw error
    return data
  },

  // Update user role (Admin only)
  async updateRole(userId, newRole) {
    const { data, error } = await supabase
      .from('profiles')
      .update({ role: newRole })
      .eq('id', userId)
      .select()
      .single()
    
    if (error) throw error
    return data
  },

  // Get all users (Admin only)
  async getAllUsers() {
    const { data, error } = await supabase
      .from('profiles')
      .select('*')
      .order('created_at', { ascending: false })
    
    if (error) throw error
    return data
  }
}

export const newsService = {
  // Get all news with pagination
  async getAllNews(filters = {}) {
    let query = supabase
      .from('news')
      .select(`
        *,
        reporter:profiles!news_reporter_id_fkey(id, username, full_name, image_url)
      `)
      .eq('soft_deleted', false)
    
    if (filters.status) {
      query = query.eq('status', filters.status)
    }
    
    if (filters.search) {
      query = query.or(`title.ilike.%${filters.search}%,short_detail.ilike.%${filters.search}%,full_detail.ilike.%${filters.search}%`)
    }
    
    query = query.order('created_at', { ascending: false })
    
    if (filters.limit) {
      query = query.limit(filters.limit)
    }
    
    if (filters.offset) {
      query = query.range(filters.offset, filters.offset + filters.limit - 1)
    }
    
    const { data, error } = await query
    
    if (error) throw error
    return data
  },

  // Get news by ID
  async getNewsById(id) {
    const { data, error } = await supabase
      .from('news')
      .select(`
        *,
        reporter:profiles!news_reporter_id_fkey(id, username, full_name, image_url),
        comments:comments(*, author:profiles!comments_user_id_fkey(id, username, full_name, image_url)),
        votes:votes(*, user:profiles!votes_user_id_fkey(id, username))
      `)
      .eq('id', id)
      .eq('soft_deleted', false)
      .single()
    
    if (error) throw error
    return data
  },

  // Create news
  async createNews(newsData) {
    const { data, error } = await supabase
      .from('news')
      .insert({
        title: newsData.title,
        short_detail: newsData.shortDetail,
        full_detail: newsData.detail,
        image_url: newsData.image || null,
        reporter_id: newsData.reporterId,
        status: 'UNDECIDED',
        created_at: new Date().toISOString()
      })
      .select()
      .single()
    
    if (error) throw error
    return data
  },

  // Update news status
  async updateNewsStatus(id, status) {
    const { data, error } = await supabase
      .from('news')
      .update({ status })
      .eq('id', id)
      .select()
      .single()
    
    if (error) throw error
    return data
  },

  // Soft delete news (Admin)
  async softDeleteNews(id) {
    const { data, error } = await supabase
      .from('news')
      .update({ soft_deleted: true })
      .eq('id', id)
      .select()
      .single()
    
    if (error) throw error
    return data
  },

  // Restore news (Admin)
  async restoreNews(id) {
    const { data, error } = await supabase
      .from('news')
      .update({ soft_deleted: false })
      .eq('id', id)
      .select()
      .single()
    
    if (error) throw error
    return data
  }
}

export const commentService = {
  // Get comments for news
  async getComments(newsId) {
    const { data, error } = await supabase
      .from('comments')
      .select(`
        *,
        author:profiles!comments_user_id_fkey(id, username, full_name, image_url)
      `)
      .eq('news_id', newsId)
      .eq('soft_deleted', false)
      .order('created_at', { ascending: false })
    
    if (error) throw error
    return data
  },

  // Create comment
  async createComment(commentData) {
    const { data, error } = await supabase
      .from('comments')
      .insert({
        news_id: commentData.newsId,
        user_id: commentData.userId,
        text: commentData.text,
        image_url: commentData.imageUrl || null,
        vote_type: commentData.voteType || null,
        created_at: new Date().toISOString()
      })
      .select()
      .single()
    
    if (error) throw error
    return data
  },

  // Soft delete comment (Admin)
  async softDeleteComment(id) {
    const { data, error } = await supabase
      .from('comments')
      .update({ soft_deleted: true })
      .eq('id', id)
      .select()
      .single()
    
    if (error) throw error
    return data
  },

  // Hard delete comment (Admin)
  async hardDeleteComment(id) {
    const { data, error } = await supabase
      .from('comments')
      .delete()
      .eq('id', id)
    
    if (error) throw error
    return data
  }
}

export const voteService = {
  // Create or update vote
  async submitVote(voteData) {
    // Check if user already voted
    const { data: existingVote } = await supabase
      .from('votes')
      .select('*')
      .eq('news_id', voteData.newsId)
      .eq('user_id', voteData.userId)
      .maybeSingle()
    
    if (existingVote) {
      // Update existing vote
      const { data, error } = await supabase
        .from('votes')
        .update({ vote_type: voteData.voteType })
        .eq('id', existingVote.id)
        .select()
        .single()
      
      if (error) throw error
      return data
    } else {
      // Create new vote
      const { data, error } = await supabase
        .from('votes')
        .insert({
          news_id: voteData.newsId,
          user_id: voteData.userId,
          vote_type: voteData.voteType,
          created_at: new Date().toISOString()
        })
        .select()
        .single()
      
      if (error) throw error
      return data
    }
  },

  // Get votes for news
  async getVotes(newsId) {
    const { data, error } = await supabase
      .from('votes')
      .select('*')
      .eq('news_id', newsId)
    
    if (error) throw error
    
    // Calculate vote counts
    const fakeCount = data.filter(v => v.vote_type === 'FAKE').length
    const notFakeCount = data.filter(v => v.vote_type === 'NOT_FAKE').length
    
    return {
      fake: fakeCount,
      notFake: notFakeCount,
      total: data.length,
      votes: data
    }
  },

  // Recalculate news status based on votes
  async recalculateNewsStatus(newsId) {
    const votes = await this.getVotes(newsId)
    let status = 'UNDECIDED'
    
    if (votes.fake > votes.notFake) {
      status = 'FAKE'
    } else if (votes.notFake > votes.fake) {
      status = 'NOT_FAKE'
    }
    
    await newsService.updateNewsStatus(newsId, status)
    return status
  }
}

// Storage helper
export const storageService = {
  async uploadImage(file, options = {}) {
    const bucket = options.bucket || 'images'
    const folder = options.folder || 'uploads'
    const fileExt = file.name?.split('.').pop() || 'jpg'
    const fileName = `${Date.now()}-${Math.random().toString(36).slice(2)}.${fileExt}`
    const filePath = `${folder}/${fileName}`

    const { error: uploadError } = await supabase.storage
      .from(bucket)
      .upload(filePath, file, {
        cacheControl: '3600',
        upsert: false
      })

    if (uploadError) throw uploadError

    const { data } = supabase.storage.from(bucket).getPublicUrl(filePath)
    return {
      path: filePath,
      publicUrl: data.publicUrl
    }
  }
}

