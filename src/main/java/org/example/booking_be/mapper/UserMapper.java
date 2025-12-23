package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.UserCreateRequest;
import org.example.booking_be.dto.request.UserUpdateRequest;
import org.example.booking_be.dto.responce.UserResponse;
import org.example.booking_be.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")

public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toEntity(UserCreateRequest request);

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateRequest request, @MappingTarget User user);
}
