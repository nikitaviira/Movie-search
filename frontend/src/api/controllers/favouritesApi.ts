import type { AxiosResponse } from 'axios';
import apiClient from '../client';

const axiosInstance = apiClient();

export enum MovieType {
  MOVIE = 'movie',
  SERIES = 'series',
  GAME = 'game'
}

export interface FavouriteMovieDto {
  title: string
  year: string
  imdbId: string
  type: MovieType
  imgUrl: string | null
}

export default {
  favourites(): Promise<AxiosResponse<FavouriteMovieDto[]>> {
    return axiosInstance.get('/favourites');
  },

  saveFavourites(movie: FavouriteMovieDto): Promise<AxiosResponse<void>> {
    return axiosInstance.post('/favourites/save', movie);
  },

  deleteFavourite(imdbId: string): Promise<AxiosResponse<void>> {
    return axiosInstance.delete(`/favourites/${imdbId}/delete`);
  }
};
