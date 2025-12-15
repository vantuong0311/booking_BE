package org.example.booking_be.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HoldingSeat {

    private String seatCode;
    private String userId;
    private LocalDateTime holdUntil;
}
