package com.domestack.omdb.client;

import feign.Param;
import lombok.Setter;

@Setter
public class FindMovieParams {
  private String imdbId;

  @Param("i")
  public String getImdbId() {
    return imdbId;
  }
}
