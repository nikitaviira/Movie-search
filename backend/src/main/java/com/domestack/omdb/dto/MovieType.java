package com.domestack.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MovieType {
  @JsonProperty("movie")
  MOVIE,
  @JsonProperty("series")
  SERIES,
  @JsonProperty("game")
  GAME
}
