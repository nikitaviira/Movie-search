package com.domestack.omdb.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteMovieDto {
  private String title;
  private String year;
  private String imdbId;
  private MovieType type;
  @Nullable
  private String imgUrl;
}
