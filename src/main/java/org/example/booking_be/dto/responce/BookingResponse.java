package org.example.booking_be.dto.responce;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponse {
    private String id;
    private String userId;
    private String showtimeId;
    private List<String> seats;
    private double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private String paymentId;
}
