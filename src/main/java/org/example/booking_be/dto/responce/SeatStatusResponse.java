package org.example.booking_be.dto.responce;

import lombok.Data;
import org.example.booking_be.enums.SeatType;

@Data
public class SeatStatusResponse {
    private String seatCode;
    private SeatType type;
    private String status; // AVAILABLE, HELD, BOOKED
}
