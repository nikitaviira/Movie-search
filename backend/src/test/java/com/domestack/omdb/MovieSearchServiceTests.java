package com.domestack.omdb;

import com.domestack.omdb.client.OmdbClient;
import com.domestack.omdb.dto.*;
import com.domestack.omdb.dto.OmdbSearchResponse.OmbdMovie;
import com.domestack.omdb.entity.FavouriteMovie;
import com.domestack.omdb.exception.NotFoundException;
import com.domestack.omdb.repository.FavouriteMovieRepository;
import com.domestack.omdb.service.MovieSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.domestack.omdb.dto.MovieType.MOVIE;
import static com.domestack.omdb.dto.MovieType.SERIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieSearchServiceTests {
  @Mock
  private OmdbClient omdbClient;
  @Mock
  private FavouriteMovieRepository favouriteMovieRepository;
  @InjectMocks
  private MovieSearchService movieSearchService;

  @Test
  void searchMovies() {
    String query = "star wars";
    int page = 1;

    var omdbResponse = new OmdbSearchResponse();
    omdbResponse.setMovies(List.of(
        new OmbdMovie("Star wars 1", "1995", "tt1233123", MOVIE, "imgUrl"),
        new OmbdMovie("Star wars 2", "1997", "tt1233asd", SERIES, "imgUrl1")
    ));
    omdbResponse.setTotal(2);

    var favouriteMovie = new FavouriteMovie();
    favouriteMovie.setImdbId("tt1233123");

    when(omdbClient.findMovies(argThat(x -> x.getQuery().equals(query) && x.getPage() == page)))
        .thenReturn(omdbResponse);
    when(favouriteMovieRepository.findAll()).thenReturn(List.of(favouriteMovie));

    var response = movieSearchService.searchMovies(query, page);

    assertThat(response.getTotal()).isEqualTo(2);
    assertThat(response.getMovies()).containsExactlyInAnyOrder(
        new MovieDto("Star wars 1", "1995", "tt1233123", MOVIE, "imgUrl", true),
        new MovieDto("Star wars 2", "1997", "tt1233asd", SERIES, "imgUrl1", false)
    );
  }

  @Test
  void searchMovies_error() {
    var omdbResponse = new OmdbSearchResponse();
    omdbResponse.setError("Error happened");
    when(omdbClient.findMovies(any())).thenReturn(omdbResponse);

    var response = movieSearchService.searchMovies("", 1);

    assertThat(response.getTotal()).isEqualTo(0);
    assertThat(response.getMovies()).isEmpty();
    verify(favouriteMovieRepository, never()).findAll();
  }

  @Test
  void findMovie() {
    String imdbId = "tt123213";
    var omdbResponse = new OmdbMovieDetailsResult("Star wars 1", "1995", imdbId, "R", "5 Jul. 1995", "120 min.",
        "Fantasy", "Nolan", "Me", "Greg", "plot", "English", "USA", "imgUrl", "1.2", "1,200,000", MOVIE, "6 Jul. 1995",
        "$1,900,000", null, null);

    var favouriteMovie = new FavouriteMovie();
    favouriteMovie.setImdbId("tt123213");

    when(omdbClient.findMovie(argThat(x -> x.getImdbId().equals(imdbId)))).thenReturn(omdbResponse);
    when(favouriteMovieRepository.findByImdbId(imdbId)).thenReturn(Optional.of(favouriteMovie));

    var response = movieSearchService.findMovie(imdbId);

    assertThat(response.getTitle()).isEqualTo("Star wars 1");
    assertThat(response.getYear()).isEqualTo("1995");
    assertThat(response.getImdbId()).isEqualTo(imdbId);
    assertThat(response.getRated()).isEqualTo("R");
    assertThat(response.getReleaseDate()).isEqualTo("5 Jul. 1995");
    assertThat(response.getRuntime()).isEqualTo("120 min.");
    assertThat(response.getGenre()).isEqualTo("Fantasy");
    assertThat(response.getDirector()).isEqualTo("Nolan");
    assertThat(response.getWriter()).isEqualTo("Me");
    assertThat(response.getActors()).isEqualTo("Greg");
    assertThat(response.getPlot()).isEqualTo("plot");
    assertThat(response.getLanguage()).isEqualTo("English");
    assertThat(response.getCountry()).isEqualTo("USA");
    assertThat(response.getImgUrl()).isEqualTo("imgUrl");
    assertThat(response.getImdbRating()).isEqualTo("1.2");
    assertThat(response.getImdbVotes()).isEqualTo("1,200,000");
    assertThat(response.getType()).isEqualTo(MOVIE);
    assertThat(response.getDvdReleaseDate()).isEqualTo("6 Jul. 1995");
    assertThat(response.getBoxOffice()).isEqualTo("$1,900,000");
  }

  @Test
  void findMovie_error() {
    var omdbResponse = new OmdbMovieDetailsResult();
    omdbResponse.setError("Error happened");
    when(omdbClient.findMovie(any())).thenReturn(omdbResponse);

    assertThatThrownBy(() -> movieSearchService.findMovie(""))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("No movie with such imdbId found");

    verify(favouriteMovieRepository, never()).findByImdbId(any());
  }
}
