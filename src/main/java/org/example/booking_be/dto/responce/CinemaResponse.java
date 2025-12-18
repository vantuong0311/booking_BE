package org.example.booking_be.dto.responce;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaResponse {
    String id;
    String name;
    String address;
}
