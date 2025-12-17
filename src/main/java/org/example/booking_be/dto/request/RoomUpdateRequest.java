package org.example.booking_be.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomUpdateRequest {
    private String name;
    private int totalSeats;
}
