package org.example.booking_be.dto.responce;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.enums.SeatType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatResponse {
    private String id;

    private String roomId;
    private String seatCode; // A1, B2...
    private SeatType type;
}
