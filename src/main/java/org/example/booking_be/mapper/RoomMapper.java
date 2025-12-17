package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.RoomCreateRequest;
import org.example.booking_be.dto.request.RoomUpdateRequest;
import org.example.booking_be.dto.responce.RoomResponse;
import org.example.booking_be.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface RoomMapper {
    @Mapping(target = "id",ignore = true)
    Room toRom(RoomCreateRequest request);

    RoomResponse toRoomResponse(Room room);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "cinemaId",ignore = true)
   void updateRoom(RoomUpdateRequest request, @MappingTarget Room room);
}
