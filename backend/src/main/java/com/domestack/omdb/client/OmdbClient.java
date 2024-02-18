package com.domestack.omdb.client;

import com.domestack.omdb.dto.OmdbMovieDetailsResult;
import com.domestack.omdb.dto.OmdbSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "omdb", url = "${omdb.apiUrl}")
public interface OmdbClient {
    @RequestMapping(method = GET)
    OmdbSearchResponse findMovies(@SpringQueryMap FindMoviesParams params);

    @RequestMapping(method = GET)
    OmdbMovieDetailsResult findMovie(@SpringQueryMap FindMovieParams params);
}