<template>
  <div class="bg-gray-100 h-[var(--container-height)] flex flex-col justify-center items-center">
    <div class="max-w-lg w-full text-center p-8">
      <h1 class="text-4xl font-bold text-gray-800 mb-4">404 - Page Not Found</h1>
      <p class="text-lg text-gray-700 mb-4">Sorry, the page you are looking for does not exist.</p>
      <a @click="toHomePage" class="text-blue-500 cursor-pointer hover:underline">Go back to home</a>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router';

  const router = useRouter();

  async function toHomePage() {
    await router.push('/');
  }
</script>
