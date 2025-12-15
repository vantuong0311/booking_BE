package org.example.booking_be.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "showtimes")

public class Showtime {

    @Id
    private String id;

    private String movieId;
    private String roomId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private double price;

    // Ghế đã thanh toán
    private List<String> bookedSeats;

    // Ghế đang giữ realtime
    private List<HoldingSeat> holdingSeats;
}
