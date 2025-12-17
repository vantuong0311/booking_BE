package org.example.booking_be.dto.responce;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RoomResponse {
    private String id;

    private String cinemaId;
    private String name;
    private int totalSeats;
}
