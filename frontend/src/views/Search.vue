<template>
  <div class="h-[var(--container-height)] flex justify-center sm:items-center sm:mt-0 mt-5">
    <div
      class="group sm:w-[600px] w-[90%] relative"
      tabindex="-1"
    >
      <input
        id="search-input"
        v-model="query"
        class="
        peer
        text-md
        w-full
        cursor-text
        appearance-none
        rounded-md
        px-6
        pb-1
        pt-6
        pr-[42px]
        text-black
        focus:outline-none
        focus:ring-0
        caret-black
        shadow-[0_4px_8px_rgba(0,0,0,0.25)]
        focus:outline-2
        focus:outline-black
        focus:outline-offset-0"
        type="text"
        placeholder=""
      >

      <SearchIcon
        class="absolute right-3 top-4 hover:fill-zinc-600 cursor-pointer fill-zinc-500"
        @click="navigateToPaginatedSearch"
      />

      <label
        class="
        text-md
        absolute
        left-6
        top-4
        z-10
        origin-[0]
        -translate-y-3
        scale-75
        transform
        text-zinc-500
        duration-150
        peer-placeholder-shown:translate-y-0
        peer-placeholder-shown:scale-100
        peer-focus:-translate-y-3
        peer-focus:scale-75"
        for="search-input"
      >
        Enter movie/series/game name
      </label>

      <div
        v-if="searchResult.length > 0"
        class="invisible group-focus-within:visible w-full absolute mt-2 rounded-md shadow-[0_4px_8px_rgba(0,0,0,0.25)]"
      >
        <div
          v-for="movie in searchResult.slice(0, 4)"
          :key="movie.imdbId"
          class="bg-white rounded-md flex px-[16px] py-[10px] hover:bg-neutral-100 cursor-pointer"
          @click="navigateToDetailsPage(movie.imdbId)"
        >
          <MoviePoster
            :height="80"
            :width="50"
            :url="movie.imgUrl"
            class="mr-[14px]"
          />
          <div class="flex flex-col self-center">
            <b>{{ movie.title }}</b>
            <p>
              <span class="capitalize">{{ movie.type }}</span>,
              {{ movie.year }}
            </p>
          </div>
          <div class="flex-grow" />
          <div class="flex items-center">
            <FavouriteButton
              :movie="movie"
              @toggled="toggleFavourite"
            >
              <HeartIcon
                :class="movie.favourite ? 'fill-red-500' : 'fill-white'"
                class="cursor-pointer"
              />
            </FavouriteButton>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useDebouncedFn } from '@/composables/useDebouncedFn';
  import { ref, watch } from 'vue';
  import SearchIcon from '@/assets/icons/SearchIcon.vue';
  import MoviePoster from '@/components/MoviePoster.vue';
  import searchApi, { type MovieDto } from '@/api/controllers/searchApi';
  import FavouriteButton from '@/components/FavouriteButton.vue';
  import { useRouter } from 'vue-router';
  import HeartIcon from '@/assets/icons/HeartIcon.vue';

  const query = ref('');
  const searchResult = ref<MovieDto[]>([]);
  const debouncedSearchResult = useDebouncedFn(() => search(), 150);
  const router = useRouter();

  watch(query, () => {
    if (query.value.length <= 2) {
      searchResult.value = [];
      return;
    }

    debouncedSearchResult();
  });

  async function search() {
    const { data } = await searchApi.searchMovies(query.value);
    searchResult.value = data.movies;
  }

  function toggleFavourite(imdbId: string) {
    const movie = searchResult.value.find((m) => m.imdbId === imdbId);
    if (movie) {
      movie.favourite = !movie.favourite;
    }
  }

  async function navigateToPaginatedSearch() {
    await router.push({ name: 'search', query: { query: query.value, page: 1 } });
  }

  async function navigateToDetailsPage(imdbId: string) {
    await router.push(`/details/${imdbId}`);
  }
</script>
