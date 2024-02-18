<template>
  <Loader v-if="loading" />
  <div
    v-if="!loading"
    class="flex w-full justify-center"
  >
    <div
      v-if="favourites.length > 0"
      class="max-w-[1200px] grid pb-5 auto-rows-min md:grid-cols-3 sm:grid-cols-2 gap-4 px-10"
    >
      <p class="md:col-span-3 sm:col-span-2 sm:text-4xl text-3xl my-5 font-bold text-center">
        My favourites
      </p>
      <MovieCard
        v-for="movie in favourites"
        :key="movie.imdbId"
        class="movie-card"
        :movie="movie"
      >
        <button
          class="inline-block ml-2 h-[40px] text-sm px-3 leading-none rounded text-white bg-red-500 hover:bg-red-400"
          @click="removeFavourite(movie.imdbId)"
        >
          <TrashCanIcon class="fill-white" />
        </button>
      </MovieCard>
    </div>
    <p
      v-else
      class="my-5 text-xl font-bold"
    >
      You dont have any favourites yet
    </p>
  </div>
</template>

<script setup lang="ts">
  import favouritesApi, { type FavouriteMovieDto } from '@/api/controllers/favouritesApi';
  import { onBeforeMount, ref } from 'vue';
  import TrashCanIcon from '@/assets/icons/TrashCanIcon.vue';
  import MovieCard from '@/components/MovieCard.vue';
  import Loader from '@/components/Loader.vue';

  const loading = ref(false);
  const favourites = ref<FavouriteMovieDto[]>([]);

  onBeforeMount(async() => {
    loading.value = true;
    const { data } = await favouritesApi.favourites();
    favourites.value = data;
    loading.value = false;
  });

  async function removeFavourite(imdbId: string) {
    await favouritesApi.deleteFavourite(imdbId);
    const index = favourites.value.findIndex((m) => m.imdbId === imdbId);
    favourites.value.splice(index, 1);
  }
</script>
