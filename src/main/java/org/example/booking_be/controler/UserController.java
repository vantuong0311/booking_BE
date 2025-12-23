package org.example.booking_be.controler;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.UserCreateRequest;
import org.example.booking_be.dto.request.UserUpdateRequest;
import org.example.booking_be.dto.responce.UserResponse;
import org.example.booking_be.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable String id,
            @RequestBody UserUpdateRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .message("Delete user successfully")
                .build();
    }
}
