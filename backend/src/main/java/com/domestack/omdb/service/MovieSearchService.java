package com.domestack.omdb.service;

import com.domestack.omdb.client.FindMovieParams;
import com.domestack.omdb.client.FindMoviesParams;
import com.domestack.omdb.client.OmdbClient;
import com.domestack.omdb.dto.*;
import com.domestack.omdb.entity.FavouriteMovie;
import com.domestack.omdb.exception.NotFoundException;
import com.domestack.omdb.repository.FavouriteMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class MovieSearchService {
  private final OmdbClient omdbClient;
  private final FavouriteMovieRepository favouriteMovieRepository;

  public MovieSearchService(OmdbClient omdbClient, FavouriteMovieRepository favouriteMovieRepository) {
    this.omdbClient = omdbClient;
    this.favouriteMovieRepository = favouriteMovieRepository;
  }

  public PaginatedSearchResponse searchMovies(String query, int page) {
    var findMoviesParams = new FindMoviesParams();
    findMoviesParams.setPage(page);
    findMoviesParams.setQuery(query);

    OmdbSearchResponse response = omdbClient.findMovies(findMoviesParams);
    if (nonNull(response.getError())) {
      return new PaginatedSearchResponse(List.of(), 0);
    }

    Set<String> favouriteMovieIds = favouriteMovieRepository.findAll().stream()
        .map(FavouriteMovie::getImdbId)
        .collect(toSet());

    return new PaginatedSearchResponse(toMovieList(response, favouriteMovieIds), response.getTotal());
  }

  public MovieDetailsDto findMovie(String imdbId) {
    var findMovieParams = new FindMovieParams();
    findMovieParams.setImdbId(imdbId);

    OmdbMovieDetailsResult movie = omdbClient.findMovie(findMovieParams);
    if (nonNull(movie.getError())) {
      throw new NotFoundException("No movie with such imdbId found");
    }

    boolean isFavourite = favouriteMovieRepository.findByImdbId(imdbId).isPresent();
    return new MovieDetailsDto(
        movie.getTitle(),
        movie.getYear(),
        movie.getImdbId(),
        isFavourite,
        movie.getRated(),
        movie.getReleaseDate(),
        movie.getRuntime(),
        movie.getGenre(),
        movie.getDirector(),
        movie.getWriter(),
        movie.getActors(),
        movie.getPlot(),
        movie.getLanguage(),
        movie.getCountry(),
        movie.getImgUrl(),
        movie.getImdbRating(),
        movie.getImdbVotes(),
        movie.getType(),
        movie.getDvdReleaseDate(),
        movie.getBoxOffice()
    );
  }

  private List<MovieDto> toMovieList(OmdbSearchResponse response, Set<String> favouriteMovieIds) {
    return response.getMovies().stream()
        .map(m -> new MovieDto(
            m.getTitle(),
            m.getYear(),
            m.getImdbId(),
            m.getType(),
            m.getImageUrl(),
            favouriteMovieIds.contains(m.getImdbId()))
        ).toList();
  }
}
