package org.example.booking_be.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.enums.SeatType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatUpdateRequest {
    private String seatCode; // A1, B2...
    private SeatType type;
//    chi cho sua 2 cai nay
}
