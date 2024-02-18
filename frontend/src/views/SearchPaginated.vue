<template>
  <div class="flex w-full h-fit justify-center">
    <Loader v-if="loading" />
    <div
      v-if="searchResult"
      class="max-w-[1200px] grid auto-rows-min bg-white md:grid-cols-3 sm:grid-cols-2 gap-4 px-10"
    >
      <p class="md:col-span-3 sm:col-span-2 sm:text-2xl text-[18px] my-5">
        Search result for: <span class="italic font-bold">{{ query }}</span> (total: {{ searchResult.total }})
      </p>
      <MovieCard
        v-for="movie in searchResult.movies"
        :key="movie.imdbId"
        :movie="movie"
      >
        <FavouriteButton
          class="flex-none"
          :movie="movie"
          @toggled="toggleFavourite"
        >
          <HeartIcon
            :class="movie.favourite ? 'fill-red-500' : 'fill-white'"
            class="cursor-pointer"
          />
        </FavouriteButton>
      </MovieCard>
      <Pagination
        :total="searchResult.total"
        :current-page="currentPage"
        :per-page="10"
        :pages-to-show="4"
        @pageChanged="(p) => currentPage = p"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useRoute, useRouter } from 'vue-router';
  import { onBeforeMount, ref, watch } from 'vue';
  import searchApi, { type PaginatedSearchResponse } from '@/api/controllers/searchApi';
  import FavouriteButton from '@/components/FavouriteButton.vue';
  import Loader from '@/components/Loader.vue';
  import Pagination from '@/components/Pagination.vue';
  import MovieCard from '@/components/MovieCard.vue';
  import HeartIcon from '@/assets/icons/HeartIcon.vue';

  const router = useRouter();
  const { query, page } = useRoute().query;
  const currentPage = ref<number>(parseInt(page as string));
  const loading = ref(false);
  const searchResult = ref<PaginatedSearchResponse>({
    total: 0,
    movies: []
  });

  onBeforeMount(() => {
    if (query) {
      search();
    }
  });

  watch(currentPage, async(value) => {
    if (query) {
      scrollToTop();
      await router.replace({ query: { query, page: value } });
      await search();
    }
  });

  async function search() {
    loading.value = true;
    const { data } = await searchApi.searchMovies(query as string, currentPage.value);
    searchResult.value = data;
    loading.value = false;
  }

  function toggleFavourite(imdbId: string) {
    const movie = searchResult.value?.movies.find((m) => m.imdbId === imdbId);
    if (movie) {
      movie.favourite = !movie.favourite;
    }
  }

  function scrollToTop() {
    document.querySelector('#app')!.scrollTo({ top: 0, behavior: 'instant' });
  }
</script>
