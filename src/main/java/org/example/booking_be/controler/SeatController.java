package org.example.booking_be.controler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.SeatCreateRequest;
import org.example.booking_be.dto.request.SeatUpdateRequest;
import org.example.booking_be.dto.responce.SeatResponse;
import org.example.booking_be.entity.Seat;
import org.example.booking_be.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SeatController {
    SeatService seatService;
    @GetMapping
    public ApiResponse<List<SeatResponse>> getAllSeats(){
        List<SeatResponse> seats = seatService.getAllSeats();
        return ApiResponse.<List<SeatResponse>>builder()
                .result(seats)
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<SeatResponse> getSeatById(@PathVariable String id){
        SeatResponse seat = seatService.getSeatById(id);
        return ApiResponse.<SeatResponse>builder().result(seat).build();
    }
    @PostMapping
    public ApiResponse<SeatResponse> createSeat(@RequestBody SeatCreateRequest request){
        SeatResponse seat = seatService.createSeat(request);
        return ApiResponse.<SeatResponse>builder().result(seat).build();
    }
    @PutMapping("/{id}")
    public ApiResponse<SeatResponse> updateSeat(@RequestBody SeatUpdateRequest request,@PathVariable String id){
        SeatResponse seat = seatService.updateSeat(id, request);
        return ApiResponse.<SeatResponse>builder()
                .result(seat)
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSeat(@PathVariable String id){
        seatService.deleteSeat(id);
        return ApiResponse.<Void>builder().message("Delete seat successfully").build();
    }


}
