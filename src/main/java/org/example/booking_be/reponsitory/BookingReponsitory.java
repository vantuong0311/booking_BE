package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingReponsitory extends MongoRepository<Booking,String> {
}
