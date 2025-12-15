package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomReponsitory extends MongoRepository<Room, String> {
    List<Room> findByCinemaId(String cinemaId);
}
