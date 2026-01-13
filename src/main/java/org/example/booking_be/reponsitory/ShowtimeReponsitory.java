package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Showtime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeReponsitory extends MongoRepository<Showtime,String> {
    List<Showtime> findByMovieId(String movieId);
}
