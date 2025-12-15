package org.example.booking_be.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_be.enums.SeatType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "seats")

public class Seat {
    @Id
    private String id;

    private String roomId;
    private String seatCode; // A1, B2...
    private SeatType type;
}
