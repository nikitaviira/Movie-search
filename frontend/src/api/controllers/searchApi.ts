import type { AxiosResponse } from 'axios';
import apiClient from '../client';
import { MovieType } from '@/api/controllers/favouritesApi';

const axiosInstance = apiClient();

export interface MovieDto {
  title: string
  year: string
  imdbId: string
  type: MovieType
  imgUrl: string | null
  favourite: boolean
}

export interface PaginatedSearchResponse {
  movies: MovieDto[]
  total: number
}

interface StringKeys {
  [key: string]: any;
}

export interface MovieDetailsDto extends StringKeys {
  title: string
  year: string
  rated: string | null
  releaseDate: string | null
  runtime: string | null
  genre: string | null
  director: string | null
  writer: string | null
  actors: string | null
  plot: string | null
  language: string | null
  country: string | null
  imgUrl: string | null
  imdbRating: string | null
  imdbVotes: string | null
  type: MovieType
  dvdReleaseDate: string | null
  boxOffice: string | null
  favourite: boolean
  imdbId: string
}

export default {
  searchMovies(query: string, page: number = 1): Promise<AxiosResponse<PaginatedSearchResponse>> {
    return axiosInstance.get('/search', { params: { query, page } });
  },

  findMovie(imdbId: string): Promise<AxiosResponse<MovieDetailsDto>> {
    return axiosInstance.get(`/find/${imdbId}`);
  }
};
