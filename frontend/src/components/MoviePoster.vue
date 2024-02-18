<template>
  <img
    v-if="url"
    :src="url"
    alt="poster"
  >
  <img
    v-else
    src="@/assets/no-image.png"
    alt="poster"
  >
</template>

<script setup lang="ts">
  defineProps<{ url: string | null }>();
</script>
