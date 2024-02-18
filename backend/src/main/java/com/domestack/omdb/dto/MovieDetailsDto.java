package com.domestack.omdb.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailsDto {
  private String title;
  private String year;
  private String imdbId;
  private boolean isFavourite;
  @Nullable
  private String rated;
  @Nullable
  private String releaseDate;
  @Nullable
  private String runtime;
  @Nullable
  private String genre;
  @Nullable
  private String director;
  @Nullable
  private String writer;
  @Nullable
  private String actors;
  @Nullable
  private String plot;
  @Nullable
  private String language;
  @Nullable
  private String country;
  @Nullable
  private String imgUrl;
  @Nullable
  private String imdbRating;
  @Nullable
  private String imdbVotes;
  @Nullable
  private MovieType type;
  @Nullable
  private String dvdReleaseDate;
  @Nullable
  private String boxOffice;
}