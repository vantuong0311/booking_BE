package org.example.booking_be.service;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.request.UserCreateRequest;
import org.example.booking_be.dto.request.UserUpdateRequest;
import org.example.booking_be.dto.responce.UserResponse;
import org.example.booking_be.entity.User;
import org.example.booking_be.enums.ErrorCode;
import org.example.booking_be.exception.AppException;
import org.example.booking_be.mapper.UserMapper;
import org.example.booking_be.reponsitory.UserReponsitory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserReponsitory userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return userMapper.toResponse(user);
    }

    public UserResponse createUser(UserCreateRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.LOGIN_FAILED);
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return userMapper.toResponse(user);
    }
//
//    public UserResponse updateUser(String id, UserUpdateRequest request) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
//
//        userMapper.updateUser(request, user);
//        userRepository.save(user);
//
//        return userMapper.toResponse(user);
//    }
//
//    public void deleteUser(String id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
//        userRepository.delete(user);
//    }
}
