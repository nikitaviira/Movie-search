import type { AxiosInstance } from 'axios';
import Axios from 'axios';
import router from '@/router';

export default (): AxiosInstance => {
  const instance = Axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL + '/api/'
  });

  instance.defaults.headers.common['Access-Control-Allow-Origin'] = import.meta.env.VITE_API_BASE_URL;

  instance.interceptors.response.use((x) => x, async(error) => {
    if (error.response.status === 404) {
      await router.push('/404');
    } else {
      throw error;
    }
  });

  return instance;
};
