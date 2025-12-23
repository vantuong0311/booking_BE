package org.example.booking_be.dto.request;

import lombok.Data;
import org.example.booking_be.enums.Role;

@Data

public class UserCreateRequest {
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private Role role;
}
