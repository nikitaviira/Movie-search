<template>
  <nav class="flex h-[var(--navbar-height)] items-center justify-between flex-wrap p-6 navbar-gradient">
    <div class="flex items-center flex-shrink-0 text-white mr-6">
      <img
        class="h-8 mr-2 cursor-pointer"
        src="@/assets/icons/DomeStack_logo1.svg"
        alt="Logo"
        @click="router.push('/')"
      >
    </div>
    <button
      class="flex items-center font-bold text-sm px-4 py-2 leading-none border rounded-3xl border-white bg-white transition hover:scale-105 hover:bg-neutral-200"
      @click="router.push('/favourites')"
    >
      <span class="mx-1">Favourites</span>
      <HeartIcon class="fill-red-600 stroke-red-600" />
    </button>
  </nav>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router';
  import HeartIcon from '@/assets/icons/HeartIcon.vue';

  const router = useRouter();
</script>

<style lang="postcss" scoped>
  .navbar-gradient {
    @apply bg-gradient-to-br from-50% from-[#080357] to-100% to-[#060424];
  }
</style>
