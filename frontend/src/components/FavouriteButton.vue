<template>
  <div @click.prevent.stop="toggleFavourite">
    <slot />
  </div>
</template>

<script setup lang="ts">
  import type { MovieDetailsDto, MovieDto } from '@/api/controllers/searchApi';
  import favouritesApi from '@/api/controllers/favouritesApi';

  const props = defineProps<{ movie: MovieDto | MovieDetailsDto }>();
  const emit = defineEmits<{ (e: 'toggled', imdbId: string): void }>();

  async function toggleFavourite() {
    if (props.movie.favourite) {
      await favouritesApi.deleteFavourite(props.movie.imdbId);
    } else {
      await favouritesApi.saveFavourites({
        title: props.movie.title,
        year: props.movie.year,
        imdbId: props.movie.imdbId,
        type: props.movie.type,
        imgUrl: props.movie.imgUrl
      });
    }

    emit('toggled', props.movie.imdbId);
  }
</script>

<style scoped>

</style>
