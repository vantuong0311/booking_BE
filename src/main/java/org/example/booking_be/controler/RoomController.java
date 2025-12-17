package org.example.booking_be.controler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.RoomCreateRequest;
import org.example.booking_be.dto.request.RoomUpdateRequest;
import org.example.booking_be.dto.responce.RoomResponse;
import org.example.booking_be.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true )
@RequiredArgsConstructor
public class RoomController {
    RoomService roomService;
    @GetMapping
    public ApiResponse<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ApiResponse.<List<RoomResponse>>builder().result(rooms).build();
    }
    @GetMapping("/{id}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable String id) {
        RoomResponse room = roomService.getRoomById(id);
        return ApiResponse.<RoomResponse>builder().result(room).build();
    }
    @PostMapping
    public ApiResponse<RoomResponse> createRoom(@RequestBody RoomCreateRequest request) {
        RoomResponse room = roomService.createRoom(request);
        return ApiResponse.<RoomResponse>builder().result(room).build();

    }
    @PutMapping("/{id}")
    public ApiResponse<RoomResponse> updateRoom(@PathVariable String id, @RequestBody RoomUpdateRequest request) {
        RoomResponse room = roomService.updateRoom(request, id);
        return ApiResponse.<RoomResponse>builder().result(room).build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return ApiResponse.<Void>builder().message("Delete seat successfully").build();

    }
}
