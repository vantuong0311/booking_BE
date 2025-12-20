package org.example.booking_be.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class HoldSeatRequest {
    private String userId;
    private List<String> seatCodes;
}

