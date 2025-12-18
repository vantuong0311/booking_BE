package org.example.booking_be.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ShowTimeCreateRequest {
    private String movieId;
    private String roomId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private double price;
}
