<template>
  <ul
    v-if="visiblePages.length > 1"
    class="md:col-span-3 sm:col-span-2 inline-flex -space-x-px text-base h-10 justify-center my-5"
  >
    <li>
      <a
        class="flex cursor-pointer items-center justify-center px-4 h-10 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700"
        @click="previousPage"
      >Previous</a>
    </li>
    <li v-if="showPreviousPageSwitcher">
      <a
        class="flex cursor-pointer items-center justify-center px-4 h-10 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 hover:bg-gray-100 hover:text-gray-700"
        @click="previouslyVisiblePage"
      >...</a>
    </li>
    <li
      v-for="page in visiblePages"
      :key="page"
    >
      <a
        class="flex cursor-pointer items-center justify-center px-4 h-10 leading-tight border border-gray-300"
        :class="page === currentPage
          ? ['text-blue-600', 'bg-blue-50', 'hover:bg-blue-100', 'hover:text-blue-700']
          : ['text-gray-500', 'bg-white', 'hover:bg-gray-100', 'hover:text-gray-700']"
        @click="switchPage(page)"
      >{{ page }}</a>
    </li>
    <li>
      <a
        class="flex cursor-pointer items-center justify-center px-4 h-10 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700"
        @click="nextPage"
      >Next</a>
    </li>
  </ul>
</template>

<script setup lang="ts">
  import { computed } from 'vue';

  const props = defineProps<{
    perPage: number,
    pagesToShow: number,
    currentPage: number,
    total: number
  }>();

  const emit = defineEmits<{ (e: 'pageChanged', page: number): void }>();

  const visiblePages = computed(() => {
    if (props.total <= props.perPage) {
      return [];
    }

    const totalPages = Math.ceil(props.total / props.perPage);
    if (totalPages <= props.pagesToShow) {
      return Array.from({ length: totalPages }, (_, i) => i + 1);
    }

    const firstVisiblePage = Math.max(1, props.currentPage - 2);
    const lastVisiblePage = Math.min(totalPages, props.currentPage + 1);

    if (lastVisiblePage - firstVisiblePage < props.pagesToShow) {
      return Array.from({ length: props.pagesToShow }, (_, i) => firstVisiblePage + i);
    }

    return [firstVisiblePage, firstVisiblePage + 1, lastVisiblePage - 1, lastVisiblePage];
  });

  const showPreviousPageSwitcher = computed(() => !visiblePages.value.includes(1));

  function previouslyVisiblePage() {
    switchPage(Math.max(1, props.currentPage - 2) - 1);
  }

  function switchPage(page: number) {
    emit('pageChanged', page);
  }

  function previousPage() {
    if (props.currentPage > 1) {
      emit('pageChanged', props.currentPage - 1);
    }
  }

  function nextPage() {
    const totalPages = Math.ceil(props.total / props.perPage);
    if (props.currentPage < totalPages) {
      emit('pageChanged', props.currentPage + 1);
    }
  }
</script>

<style scoped>

</style>
