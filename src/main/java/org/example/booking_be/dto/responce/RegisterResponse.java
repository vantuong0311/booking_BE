package org.example.booking_be.dto.responce;

import lombok.Data;

@Data
public class RegisterResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String role;
}
