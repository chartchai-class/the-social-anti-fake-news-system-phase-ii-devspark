<template>
  <div class="flex justify-center mt-4 space-x-2">
    <button
      v-for="n in totalPages"
      :key="n"
      @click="$emit('update:page', n)"
      :class="[
        'px-3 py-1 border rounded transition', 
        n === page ? 'bg-blue-500 text-white border-blue-500' : 'bg-white text-gray-700 border-gray-300 hover:bg-gray-100'
      ]"
    >
      {{ n }}
    </button>
  </div>
</template>

<script setup>
import { computed } from 'vue'
defineProps({ total: Number, perPage: Number, page: Number })

const totalPages = computed(() => Math.ceil(total / perPage))
</script>
