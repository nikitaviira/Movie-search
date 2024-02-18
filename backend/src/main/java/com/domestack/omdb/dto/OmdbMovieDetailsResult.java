package com.domestack.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmdbMovieDetailsResult {
  @JsonProperty("Title")
  private String title;

  @JsonProperty("Year")
  private String year;

  @JsonProperty("imdbID")
  private String imdbId;

  @Nullable
  @JsonProperty("Rated")
  private String rated;

  @Nullable
  @JsonProperty("Released")
  private String releaseDate;

  @Nullable
  @JsonProperty("Runtime")
  private String runtime;

  @Nullable
  @JsonProperty("Genre")
  private String genre;

  @Nullable
  @JsonProperty("Director")
  private String director;

  @Nullable
  @JsonProperty("Writer")
  private String writer;

  @Nullable
  @JsonProperty("Actors")
  private String actors;

  @Nullable
  @JsonProperty("Plot")
  private String plot;

  @Nullable
  @JsonProperty("Language")
  private String language;

  @Nullable
  @JsonProperty("Country")
  private String country;

  @Nullable
  @JsonProperty("Poster")
  private String imgUrl;

  @Nullable
  private String imdbRating;

  @Nullable
  private String imdbVotes;

  @Nullable
  @JsonProperty("Type")
  private MovieType type;

  @Nullable
  @JsonProperty("DVD")
  private String dvdReleaseDate;

  @Nullable
  @JsonProperty("BoxOffice")
  private String boxOffice;

  @Nullable
  private Integer totalSeasons;

  @JsonProperty("Error")
  @Nullable
  private String error;
}