<template>
  <div class="max-w-2xl mx-auto px-4">
    <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-6 my-4">
      <h2 class="text-2xl font-bold mb-4">Register</h2>

      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-700 mb-1">Username *</label>
        <input v-model="form.username" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" />
      </div>

      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-700 mb-1">Email *</label>
        <input v-model="form.email" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" type="email" />
      </div>

      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-700 mb-1">Password *</label>
        <input v-model="form.password" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" type="password" />
      </div>

      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-700 mb-1">Confirm Password *</label>
        <input v-model="form.confirmPassword" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" type="password" />
      </div>

      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-700 mb-1">Display name</label>
        <input v-model="form.displayName" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" />
      </div>

      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-700 mb-1">Profile image (URL)</label>
        <input v-model="form.image" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" placeholder="/images/avatar1.jpg or https://..." />
      </div>

      <div v-if="form.image" style="margin-bottom:.6rem">
        <label>Preview</label>
        <img :src="form.image" alt="preview" style="width:80px;height:80px;border-radius:8px;object-fit:cover;" />
      </div>

      <div class="flex gap-2">
        <button class="px-4 py-2 rounded-full bg-blue-600 hover:bg-blue-700 text-white font-semibold shadow" @click="submit">Register</button>
        <router-link class="px-4 py-2 rounded-full bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold border border-gray-300" to="/">Cancel</router-link>
      </div>

      <p class="mt-2 text-slate-600 text-sm">Already have an account? <router-link to="/" class="text-blue-600 hover:underline">Sign in</router-link></p>
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
