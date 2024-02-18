package com.domestack.omdb.client;

import feign.Param;
import lombok.Getter;
import lombok.Setter;

@Setter
public class FindMoviesParams {
  private String query;
  @Getter
  private int page = 1;

  @Param("s")
  public String getQuery() {
    return query;
  }
}
