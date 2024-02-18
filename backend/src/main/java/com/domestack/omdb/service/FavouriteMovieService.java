package com.domestack.omdb.service;

import com.domestack.omdb.dto.FavouriteMovieDto;
import com.domestack.omdb.entity.FavouriteMovie;
import com.domestack.omdb.exception.NotFoundException;
import com.domestack.omdb.repository.FavouriteMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteMovieService {
  private final FavouriteMovieRepository favouriteMovieRepository;

  public FavouriteMovieService(FavouriteMovieRepository favouriteMovieRepository) {
    this.favouriteMovieRepository = favouriteMovieRepository;
  }

  public List<FavouriteMovieDto> favourites() {
    return favouriteMovieRepository.findAll().stream()
        .map(m -> new FavouriteMovieDto(
            m.getTitle(),
            m.getYear(),
            m.getImdbId(),
            m.getMovieType(),
            m.getImgUrl())
        ).toList();
  }

  public void saveFavourite(FavouriteMovieDto movie) {
    var favouriteMovie = new FavouriteMovie();
    favouriteMovie.setYear(movie.getYear());
    favouriteMovie.setTitle(movie.getTitle());
    favouriteMovie.setImdbId(movie.getImdbId());
    favouriteMovie.setMovieType(movie.getType());
    favouriteMovie.setImgUrl(movie.getImgUrl());
    favouriteMovieRepository.save(favouriteMovie);
  }

  public void deleteFavourite(String imdbId) {
    favouriteMovieRepository.findByImdbId(imdbId).ifPresentOrElse(
        favouriteMovieRepository::delete,
        () -> {
          throw new NotFoundException("No favourite movie with such imdbId found");
        });
  }
}
