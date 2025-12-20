package org.example.booking_be.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    private String userId;
    private List<String> seats;
}
