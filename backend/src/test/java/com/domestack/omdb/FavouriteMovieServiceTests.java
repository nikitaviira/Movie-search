package com.domestack.omdb;

import com.domestack.omdb.dto.FavouriteMovieDto;
import com.domestack.omdb.entity.FavouriteMovie;
import com.domestack.omdb.exception.NotFoundException;
import com.domestack.omdb.repository.FavouriteMovieRepository;
import com.domestack.omdb.service.FavouriteMovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.domestack.omdb.dto.MovieType.MOVIE;
import static com.domestack.omdb.dto.MovieType.SERIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavouriteMovieServiceTests {
  @Mock
  private FavouriteMovieRepository favouriteMovieRepository;
  @InjectMocks
  private FavouriteMovieService favouriteMovieService;

  @Test
  void favourites() {
    var m1 = new FavouriteMovie();
    m1.setImdbId("tt123123");
    m1.setYear("1995");
    m1.setMovieType(MOVIE);
    m1.setTitle("Star wars 1");
    m1.setImgUrl("imgUrl");

    var m2 = new FavouriteMovie();
    m2.setImdbId("tt125555");
    m2.setYear("1998");
    m2.setMovieType(SERIES);
    m2.setTitle("Star wars 2");
    m2.setImgUrl("imgUrl 2");

    when(favouriteMovieRepository.findAll()).thenReturn(List.of(m1, m2));

    assertThat(favouriteMovieService.favourites()).containsExactly(
        new FavouriteMovieDto("Star wars 1", "1995", "tt123123", MOVIE, "imgUrl"),
        new FavouriteMovieDto("Star wars 2", "1998", "tt125555", SERIES, "imgUrl 2")
    );
  }

  @Test
  void saveFavourite() {
    var movieToSave = new FavouriteMovieDto("Star wars 1", "1995", "tt123123", MOVIE, "imgUrl");

    favouriteMovieService.saveFavourite(movieToSave);

    ArgumentCaptor<FavouriteMovie> captor = forClass(FavouriteMovie.class);
    verify(favouriteMovieRepository).save(captor.capture());

    var favouriteMovie = captor.getValue();
    assertThat(favouriteMovie.getTitle()).isEqualTo("Star wars 1");
    assertThat(favouriteMovie.getYear()).isEqualTo("1995");
    assertThat(favouriteMovie.getImdbId()).isEqualTo("tt123123");
    assertThat(favouriteMovie.getMovieType()).isEqualTo(MOVIE);
    assertThat(favouriteMovie.getImgUrl()).isEqualTo("imgUrl");
  }

  @Test
  void deleteFavourite() {
    String imdbId = "tt123123";
    var favouriteMovie = new FavouriteMovie();
    when(favouriteMovieRepository.findByImdbId(imdbId)).thenReturn(Optional.of(favouriteMovie));

    favouriteMovieService.deleteFavourite(imdbId);

    verify(favouriteMovieRepository).delete(favouriteMovie);
  }

  @Test
  void deleteFavourite_notFound() {
    when(favouriteMovieRepository.findByImdbId(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> favouriteMovieService.deleteFavourite(""))
        .isInstanceOf(NotFoundException.class)
            .hasMessage("No favourite movie with such imdbId found");

    verify(favouriteMovieRepository, never()).delete(any());
  }
}
