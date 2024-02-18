package com.domestack.omdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedSearchResponse {
  private List<MovieDto> movies;
  private int total;
}
