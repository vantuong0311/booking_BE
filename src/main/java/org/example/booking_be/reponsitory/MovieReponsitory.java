package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Movie;
import org.example.booking_be.enums.MovieStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieReponsitory extends MongoRepository<Movie, String> {
    List<Movie> findByStatus(MovieStatus status);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByGenreId(String genreId);
}
