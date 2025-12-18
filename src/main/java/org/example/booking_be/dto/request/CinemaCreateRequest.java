package org.example.booking_be.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CinemaCreateRequest {
    String name;
    String address;
}
