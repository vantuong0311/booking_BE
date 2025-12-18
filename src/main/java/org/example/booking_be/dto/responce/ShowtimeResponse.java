package org.example.booking_be.dto.responce;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class ShowtimeResponse {
    private String id;

    private String movieId;
    private String roomId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private double price;

    // Ghế đã thanh toán
    private List<String> bookedSeats;
}
