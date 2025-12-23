package org.example.booking_be.dto.request;

import lombok.Data;
import org.example.booking_be.enums.Role;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String phone;
    private Role role;
}
