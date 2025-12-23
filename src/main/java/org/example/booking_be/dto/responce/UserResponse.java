package org.example.booking_be.dto.responce;

import lombok.Builder;
import lombok.Data;
import org.example.booking_be.enums.Role;

import java.time.LocalDateTime;
@Data
@Builder

public class UserResponse {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private LocalDateTime createdAt;
}
