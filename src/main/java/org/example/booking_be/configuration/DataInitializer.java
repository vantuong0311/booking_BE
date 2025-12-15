package org.example.booking_be.configuration;


import lombok.RequiredArgsConstructor;
import org.example.booking_be.entity.*;
import org.example.booking_be.enums.BookingStatus;
import org.example.booking_be.reponsitory.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserReponsitory userRepo,
            MovieReponsitory movieRepo,
            CinemaReponsitory cinemaRepo,
            RoomReponsitory roomRepo,
            SeatReponsitory seatRepo,
            ShowtimeReponsitory showtimeRepo,
            BookingReponsitory bookingRepo
    ) {
        return args -> {

            if (userRepo.count() == 0)
                userRepo.save(new User());

            if (movieRepo.count() == 0)
                movieRepo.save(new Movie());
            if (cinemaRepo.count() == 0)
                cinemaRepo.save(new Cinema());

            if (roomRepo.count() == 0)
                roomRepo.save(new Room());

            if (seatRepo.count() == 0)
                seatRepo.save(new Seat());

            if (showtimeRepo.count() == 0)
                showtimeRepo.save(new Showtime());

            if (bookingRepo.count() == 0)
//                bookingRepo.save(new Booking("1","1","1", null,50, BookingStatus.PAID, LocalDateTime.now()));
            {
                Booking bk = new Booking("1","1","1", null,50, BookingStatus.PAID, LocalDateTime.now());
                Booking bks = new Booking("2","2","2", List.of("1","2"), 50, BookingStatus.PAID, LocalDateTime.now());
                bookingRepo.save(bks);
            }
        };
    }
}
