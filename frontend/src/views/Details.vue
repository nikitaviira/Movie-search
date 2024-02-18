<template>
  <Loader v-if="loading" />
  <div
    v-if="movieDetails && !loading"
    class="xl:max-w-[1280px] max-w-[1024px] m-auto bg-white lg:px-4 px-6"
  >
    <button
      v-if="showGoBack"
      class="text-blue-500 mt-2 cursor-pointer hover:underline"
      @click="goBack"
    >
      Back to search
    </button>
    <div class="xl:max-w-[1200px] max-w-[960px] w-full me-auto ms-auto">
      <div class="flex lg:flex-row flex-col">
        <!--Poster-->
        <div class="xl:w-[320px] xl:flex-[0_0_320px] lg:w-[240px] lg:flex-[0_0_240px] ">
          <div class="flex flex-col h-full ">
            <div class="flex justify-center lg:justify-normal lg:flex-1 lg:flex-col lg:p-[40px_0_40px] pt-[40px] w-full">
              <MoviePoster
                class="mb-[24px]"
                :url="movieDetails.imgUrl"
              />
            </div>
          </div>
        </div>

        <!--Delimiter-->
        <div class="hidden lg:block flex-[0_0_30px] w-[30px]" />

        <!--Main content-->
        <div class="xl:flex-[0_0_840px] xl:w-[840px] lg:flex-[0_0_680px] lg:w-[680px]">
          <div class="flex flex-col sm:p-[40px_0_40px] pb-5">
            <!-- Title and rating -->
            <div class="flex sm:mb-[48px] sm:flex-row sm:pb-0 pb-4 flex-col sm:self-stretch sm:items-stretch self-center items-center">
              <div class="middle-col">
                <div class="sm:mb-[16px]">
                  <h1 class="sm:text-start text-center break-words sm:text-[40px] text-[30px] font-bold tracking-[-.5px]">
                    {{ movieDetails.title }}
                  </h1>
                </div>

                <div class="sm:mt-[25px] mt-2 sm:block flex justify-center">
                  <FavouriteButton
                    :movie="movieDetails"
                    @toggled="toggleFavourite"
                  >
                    <button class="flex items-center font-bold text-sm px-4 py-2 leading-none border rounded-3xl border-white bg-neutral-200 transition hover:scale-105 hover:bg-neutral-300">
                      <span class="mx-1">{{ movieDetails.favourite ? 'Remove from favourites' : 'Add to favourites' }}</span>
                      <HeartIcon :class="movieDetails.favourite ? 'fill-red-500' : 'fill-none'" />
                    </button>
                  </FavouriteButton>
                </div>
              </div>

              <div class="end-col sm:mt-0 mt-2">
                <div class="pl-[20px] mt-[6px]">
                  <div class="flex flex-col items-baseline leading-[30px]">
                    <span
                      :class="movieDetails.imdbRating ? imdbRatingColor(movieDetails.imdbRating) : 'text-black'"
                      class="text-[32px] font-bold"
                    >{{ valueOrDefault(movieDetails.imdbRating) }}</span>
                    <span class="text-neutral-500 text-[13px]">{{ valueOrDefault(movieDetails.imdbVotes) }} votes</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Movie description -->
            <div class="flex sm:flex-row flex-col">
              <div class="middle-col">
                <h3 class="text-[22px] mb-[9px] font-semibold">
                  About movie
                </h3>
                <div>
                  <div
                    v-for="[key, val] in Object.entries(tableRows)"
                    :key="key"
                    class="flex py-[8px] items-baseline"
                  >
                    <div class="min-w-[160px] text-[14px] text-neutral-500">
                      {{ val }}
                    </div>
                    <span class="text-[14px] font-bold">{{ valueOrDefault(movieDetails[key]) }}</span>
                  </div>
                </div>
              </div>

              <div
                v-if="movieDetails.actors"
                class="end-col"
              >
                <div class="sm:pl-[20px] mt-[20px]">
                  <h3 class="text-[17px] mb-[15px] font-semibold">
                    Main actors
                  </h3>
                  <ul class="sm:block hidden">
                    <li
                      v-for="actor in movieDetails.actors.split(',')"
                      :key="actor"
                      class="mb-[7px] text-[14px]"
                    >
                      {{ actor }}
                    </li>
                  </ul>
                  <p class="sm:hidden block">
                    {{ movieDetails.actors }}
                  </p>
                </div>
              </div>
            </div>
            <div
              v-if="movieDetails.plot"
              class="mt-3 py-2 border-t xl:max-w-[600px] lg:max-w-[440px] w-full"
            >
              <h3 class="text-[17px] mb-[15px] font-semibold">
                Plot
              </h3>
              <p>{{ movieDetails.plot }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import HeartIcon from '@/assets/icons/HeartIcon.vue';
  import { computed, onBeforeMount, ref } from 'vue';
  import type { MovieDetailsDto } from '@/api/controllers/searchApi';
  import { useRoute, useRouter } from 'vue-router';
  import searchApi from '@/api/controllers/searchApi';
  import Loader from '@/components/Loader.vue';
  import MoviePoster from '@/components/MoviePoster.vue';
  import FavouriteButton from '@/components/FavouriteButton.vue';

  const router = useRouter();
  const { imdbId } = useRoute().params;
  const loading = ref(false);
  const movieDetails = ref<MovieDetailsDto>();

  const showGoBack = computed(() => {
    const backUrl = router.options.history.state.back as string | undefined;
    return backUrl !== undefined && backUrl.includes('/search');
  });

  const tableRows: Partial<Record<keyof MovieDetailsDto, string>> = {
    year: 'Year',
    country: 'Country',
    genre: 'Genre',
    director: 'Director',
    writer: 'Writer',
    releaseDate: 'Released',
    dvdReleaseDate: 'Released on DVD',
    runtime: 'Runtime',
    language: 'Language',
    boxOffice: 'Box office',
    rated: 'Rated'
  };

  onBeforeMount(async() => {
    loading.value = true;
    const { data } = await searchApi.findMovie(imdbId as string);
    movieDetails.value = data;
    loading.value = false;
  });

  function valueOrDefault(value: any) {
    return value ?? 'N/A';
  }

  function toggleFavourite() {
    if (movieDetails.value) {
      movieDetails.value.favourite = !movieDetails.value.favourite;
    }
  }

  function goBack() {
    router.go(-1);
  }

  function imdbRatingColor(imdbRating: string): string {
    const rating = parseInt(imdbRating);
    if (rating >= 7) {
      return 'text-[#3bb33b]';
    } else if (rating > 5) {
      return 'text-neutral-500';
    }
    return 'text-red-500';
  }
</script>

<style lang="postcss" scoped>
  .middle-col {
    @apply xl:flex-[0_0_600px] xl:w-[600px] lg:flex-[0_0_440px] lg:w-[440px] w-full;
  }

  .end-col {
    @apply lg:flex-[0_0_240px] lg:w-[240px] md:flex-[0_0_200px] md:w-[200px] sm:flex-[0_0_150px] sm:w-[150px];
  }
</style>
