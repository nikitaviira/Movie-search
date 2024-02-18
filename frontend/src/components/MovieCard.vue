<template>
  <div class="bg-white rounded-md border shadow-xl p-4 relative transition hover:scale-[102%] hover:shadow-neutral-400">
    <MoviePoster
      :url="movie.imgUrl"
      class="rounded-md cursor-pointer max-h-[400px] mx-auto"
      @click="navigateToDetailsPage(movie.imdbId)"
    />
    <div class="mt-4 flex justify-between">
      <div>
        <h3 class="text-lg font-semibold">
          {{ movie.title }}
        </h3>
        <p class="text-gray-500">
          <span class="capitalize">{{ movie.type }}</span>, {{ movie.year }}
        </p>
      </div>
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
  import MoviePoster from '@/components/MoviePoster.vue';
  import { useRouter } from 'vue-router';
  import type { MovieDto } from '@/api/controllers/searchApi';

  defineProps<{ movie: MovieDto }>();
  const router = useRouter();

  async function navigateToDetailsPage(imdbId: string) {
    await router.push(`/details/${imdbId}`);
  }
</script>
