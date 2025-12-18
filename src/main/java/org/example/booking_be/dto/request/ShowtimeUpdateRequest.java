package org.example.booking_be.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowtimeUpdateRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private double price;
}
