package org.example.booking_be.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.enums.SeatType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SeatCreateRequest {

    private String roomId;
    private String seatCode; // A1, B2...
    private SeatType type;

}
