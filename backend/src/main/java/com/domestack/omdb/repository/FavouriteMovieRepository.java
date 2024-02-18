package com.domestack.omdb.repository;

import com.domestack.omdb.entity.FavouriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteMovieRepository extends JpaRepository<FavouriteMovie, Long> {
  Optional<FavouriteMovie> findByImdbId(String imdbId);
}
