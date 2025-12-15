package org.example.booking_be.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.example.booking_be.enums.BookingStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private String userId;
    private String showtimeId;
    private List<String> seats;
    private double totalAmount;
    private BookingStatus status;

    private LocalDateTime createdAt;

//    public Booking(String id, String userId, String showtimeId, List<String> seats, double totalAmount, BookingStatus status, LocalDateTime createdAt) {
//        this.id = id;
//        this.userId = userId;
//        this.showtimeId = showtimeId;
//        this.seats = seats;
//        this.totalAmount = totalAmount;
//        this.status = status;
//        this.createdAt = createdAt;
//    }
}
