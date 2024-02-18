package com.domestack.omdb.controller;

import com.domestack.omdb.dto.FavouriteMovieDto;
import com.domestack.omdb.service.FavouriteMovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class FavouriteMovieController {
  private final FavouriteMovieService favouriteMovieService;

  public FavouriteMovieController(FavouriteMovieService favouriteMovieService) {
    this.favouriteMovieService = favouriteMovieService;
  }

  @GetMapping("/favourites")
  public List<FavouriteMovieDto> favourites() {
    return favouriteMovieService.favourites();
  }

  @PostMapping("/favourites/save")
  public void saveFavourite(@RequestBody FavouriteMovieDto movie) {
    favouriteMovieService.saveFavourite(movie);
  }

  @DeleteMapping("/favourites/{imdbId}/delete")
  public void deleteFavourite(@PathVariable String imdbId) {
    favouriteMovieService.deleteFavourite(imdbId);
  }
}
