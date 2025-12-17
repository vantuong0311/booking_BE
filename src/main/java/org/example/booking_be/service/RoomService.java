package org.example.booking_be.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.request.RoomCreateRequest;
import org.example.booking_be.dto.request.RoomUpdateRequest;
import org.example.booking_be.dto.responce.RoomResponse;
import org.example.booking_be.entity.Room;
import org.example.booking_be.enums.ErrorCode;
import org.example.booking_be.exception.AppException;
import org.example.booking_be.mapper.RoomMapper;
import org.example.booking_be.reponsitory.RoomReponsitory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class RoomService {
    RoomMapper roomMapper;
    RoomReponsitory roomReponsitory;
    public List<RoomResponse> getAllRooms() {
        List<Room> rooms = roomReponsitory.findAll();
        return rooms.stream()
                .map(roomMapper:: toRoomResponse)
                .collect(Collectors.toList());
    }
    public RoomResponse getRoomById(String id) {
        Room room = roomReponsitory.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        return roomMapper.toRoomResponse(room);
    }
    public RoomResponse createRoom(RoomCreateRequest request){
        Room room = roomMapper.toRom(request);
        roomReponsitory.save(room);
        return roomMapper.toRoomResponse(room);

    }

    public RoomResponse updateRoom(RoomUpdateRequest request,String id){

        Room room = roomReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        roomMapper.updateRoom(request, room);
        roomReponsitory.save(room);
        return roomMapper.toRoomResponse(room);
    }

    public void deleteRoom(String id){
        Room room = roomReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        roomReponsitory.delete(room);
    }


}
