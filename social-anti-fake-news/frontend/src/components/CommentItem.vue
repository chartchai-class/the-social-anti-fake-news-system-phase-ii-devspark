<template>
  <div class="border-t pt-3 mt-3 flex items-start space-x-3">
    <img 
      :src="comment.imageUrl || 'https://via.placeholder.com/40'" 
      alt="user image" 
      class="w-10 h-10 rounded-full object-cover border"
    />
    <div class="flex-1">
      <p class="font-semibold text-gray-800">{{ comment.user }}</p>
      <p class="text-sm text-gray-600">{{ comment.text }}</p>
      <img v-if="comment.imageUrl" :src="comment.imageUrl" class="mt-2 max-h-40 rounded object-cover" />
    </div>
  </div>
</template>

<script setup>
defineProps({
  comment: {
    type: Object,
    required: true
  }
})
</script>
