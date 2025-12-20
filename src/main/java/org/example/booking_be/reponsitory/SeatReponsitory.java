package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatReponsitory extends MongoRepository<Seat, String> {
    List<Seat> findByRoomId(String roomId);
}
