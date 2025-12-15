package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReponsitory extends MongoRepository<Movie, String> {
}
