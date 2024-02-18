package com.domestack.omdb.entity;

import com.domestack.omdb.dto.MovieType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
@Table(name = "favourite_movie")
public class FavouriteMovie {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String year;
  private String title;
  @Column(name = "imdb_id")
  private String imdbId;
  @Nullable
  @Column(name = "img_url")
  private String imgUrl;
  @Column(name = "movie_type")
  private MovieType movieType;
}
