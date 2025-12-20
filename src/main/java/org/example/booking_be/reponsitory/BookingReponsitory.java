package org.example.booking_be.reponsitory;

import org.example.booking_be.entity.Booking;
import org.example.booking_be.enums.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingReponsitory extends MongoRepository<Booking,String> {
    List<Booking> findByUserId(String userId);

    List<Booking> findByShowtimeId(String showtimeId);

    List<Booking> findByStatusAndExpiredAtBefore(BookingStatus status, LocalDateTime dateTime);
}
