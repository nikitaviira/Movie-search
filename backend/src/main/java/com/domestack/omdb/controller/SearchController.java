package com.domestack.omdb.controller;

import com.domestack.omdb.dto.MovieDetailsDto;
import com.domestack.omdb.dto.PaginatedSearchResponse;
import com.domestack.omdb.service.MovieSearchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class SearchController {
  private final MovieSearchService searchService;

  public SearchController(MovieSearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping("/search")
  public PaginatedSearchResponse searchMovies(@RequestParam String query,
                                              @RequestParam(required = false, defaultValue = "1") int page) {
    return searchService.searchMovies(query, page);
  }

  @GetMapping("/find/{imdbId}")
  public MovieDetailsDto findMovie(@PathVariable String imdbId) {
    return searchService.findMovie(imdbId);
  }
}