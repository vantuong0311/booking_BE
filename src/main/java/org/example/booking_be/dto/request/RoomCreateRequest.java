package org.example.booking_be.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomCreateRequest {
    private String cinemaId;
    private String name;
    private int totalSeats;
}
