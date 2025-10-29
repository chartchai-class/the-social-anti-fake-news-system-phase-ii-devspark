<template>
  <div class="container">
    <div class="card" style="max-width:720px;margin:1rem auto;">
      <h2>Register</h2>

      <div class="form-row">
        <label>Username *</label>
        <input v-model="form.username" class="input" />
      </div>

      <div class="form-row">
        <label>Email *</label>
        <input v-model="form.email" class="input" type="email" />
      </div>

      <div class="form-row">
        <label>Password *</label>
        <input v-model="form.password" class="input" type="password" />
      </div>

      <div class="form-row">
        <label>Confirm Password *</label>
        <input v-model="form.confirmPassword" class="input" type="password" />
      </div>

      <div class="form-row">
        <label>Display name</label>
        <input v-model="form.displayName" class="input" />
      </div>

      <div class="form-row">
        <label>Profile image (URL)</label>
        <input v-model="form.image" class="input" placeholder="/images/avatar1.jpg or https://..." />
      </div>

      <div v-if="form.image" style="margin-bottom:.6rem">
        <label>Preview</label>
        <img :src="form.image" alt="preview" style="width:80px;height:80px;border-radius:8px;object-fit:cover;" />
      </div>

      <div style="display:flex;gap:.5rem">
        <button class="btn btn-primary" @click="submit">Register</button>
        <router-link class="btn btn-ghost" to="/">Cancel</router-link>
      </div>

      <p style="margin-top:.6rem;color:#475569;font-size:.9rem">Already have an account? <router-link to="/">Sign in</router-link></p>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useNewsStore } from '../store/newsStore'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const ns = useNewsStore()
    const router = useRouter()
    const form = ref({
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      displayName: '',
      image: ''
    })

    function validate() {
      if (!form.value.username.trim()) return 'Username required'
      if (!form.value.email.trim()) return 'Email required'
      if (!form.value.password) return 'Password required'
      if (form.value.password.length < 6) return 'Password must be ≥ 6 chars'
      if (form.value.password !== form.value.confirmPassword) return 'Password confirmation mismatch'
      return null
    }

    async function submit() {
      const err = validate()
      if (err) return alert(err)

      // if backend API exists, replace block below with POST /api/users/register
      // Example (axios):
      // await axios.post('/api/users/register', {...})

      // For grading: store minimal user in client store (mock)
      ns.user = {
        id: Date.now(),
        name: form.value.displayName || form.value.username,
        username: form.value.username,
        email: form.value.email,
        isAdmin: false,
        role: 'USER'
      }

      alert('Registered (mock). If your backend has /users/register, replace this block with an API call.')
      router.push('/')
    }

    return { form, submit }
  }
}
</script>
