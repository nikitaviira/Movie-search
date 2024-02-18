package com.domestack.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class OmdbSearchResponse {
    @JsonProperty("Search")
    private List<OmbdMovie> movies;

    @JsonProperty("totalResults")
    private int total;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    @Nullable
    private String error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OmbdMovie {
        @JsonProperty("Title")
        private String title;

        @JsonProperty("Year")
        private String year;

        @JsonProperty("imdbID")
        private String imdbId;

        @JsonProperty("Type")
        private MovieType type;

        @JsonProperty("Poster")
        @Nullable
        private String imageUrl;
    }
}