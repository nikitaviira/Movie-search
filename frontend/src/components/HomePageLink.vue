<template>
  <a
    class="text-blue-500 cursor-pointer hover:underline"
    @click="toHomePage"
  >Go back to home</a>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router';

  const router = useRouter();

  async function toHomePage() {
    await router.push('/');
  }
</script>
