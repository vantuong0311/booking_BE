package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Cinema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaReponsitory extends MongoRepository<Cinema, String> {
}
