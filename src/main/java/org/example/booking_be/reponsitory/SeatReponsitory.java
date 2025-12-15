package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatReponsitory extends MongoRepository<Seat, String> {
}
