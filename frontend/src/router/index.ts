import { createRouter, createWebHistory } from 'vue-router';
import Search from '@/views/Search.vue';
import SearchPagination from '@/views/SearchPaginated.vue';
import Details from '@/views/Details.vue';
import Favourites from '@/views/Favourites.vue';
import PageNotFound from "@/views/PageNotFound.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Search
    },
    {
      path: '/search',
      name: 'search',
      component: SearchPagination
    },
    {
      path: '/favourites',
      name: 'favourites',
      component: Favourites
    },
    {
      path: '/details/:imdbId',
      name: 'details',
      component: Details
    },
    {
      path: '/404',
      component: PageNotFound
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/404'
    }
  ]
});

export default router;
