package org.example.booking_be.controler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.HoldSeatRequest;
import org.example.booking_be.dto.request.ShowTimeCreateRequest;
import org.example.booking_be.dto.request.ShowtimeUpdateRequest;
import org.example.booking_be.dto.responce.SeatStatusResponse;
import org.example.booking_be.dto.responce.ShowtimeResponse;
import org.example.booking_be.service.ShowTimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/showtimes")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ShowtimeController {
    ShowTimeService showTimeService;
    @GetMapping
    public ApiResponse<List<ShowtimeResponse>> getAllShowtimes() {
        List<ShowtimeResponse> showtimes= showTimeService.getAllShowTimes();
        return ApiResponse.<List<ShowtimeResponse>>builder().result(showtimes).build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ShowtimeResponse> getShowtimeById(@PathVariable String id){
        ShowtimeResponse showtime = showTimeService.getShowtimeById(id);
        return ApiResponse.<ShowtimeResponse>builder().result(showtime).build();
    }

    @PostMapping
    public ApiResponse<ShowtimeResponse> createShowtime(@RequestBody ShowTimeCreateRequest request){
        ShowtimeResponse showtime = showTimeService.createShowTime(request);
        return ApiResponse.<ShowtimeResponse>builder().result(showtime).build();
    }
    @PutMapping("/{id}")
    public ApiResponse<ShowtimeResponse> updateShowtime(@RequestBody ShowtimeUpdateRequest request,@PathVariable String id){
        ShowtimeResponse showtime = showTimeService.updateShowTime(id, request);
        return ApiResponse.<ShowtimeResponse>builder().result(showtime).build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteShowtime(@PathVariable String id){
        showTimeService.delete(id);
        return ApiResponse.<Void>builder().result(null).build();
    }
    @PostMapping("/{id}/hold")
    public ApiResponse<Void> holdSeat(
            @PathVariable String id,
            @RequestBody HoldSeatRequest request
    ) {
        showTimeService.holdSeats(id, request.getSeatCodes(), request.getUserId());
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/{id}/release")
    public ApiResponse<Void> releaseSeat(
            @PathVariable String id,
            @RequestParam String userId) {
        showTimeService.releaseSeats(id, userId);
        return ApiResponse.<Void>builder().build();
    }


    @PostMapping("/{id}/confirm")
    public ApiResponse<Void> confirmBooking(
            @PathVariable String id,
            @RequestBody List<String> seats,
            @RequestParam String userId) {
        showTimeService.confirmBooking(id, seats, userId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/{id}/seats")
    public ApiResponse<List<SeatStatusResponse>> getSeatsStatus(@PathVariable String id) {
        List<SeatStatusResponse> seats = showTimeService.getSeatsStatus(id);
        return ApiResponse.<List<SeatStatusResponse>>builder().result(seats).build();
    }

    @GetMapping("/movie/{movieId}")
    public ApiResponse<List<ShowtimeResponse>> getShowtimesByMovieId(
            @PathVariable String movieId
    ) {
        List<ShowtimeResponse> showtimes =
                showTimeService.getShowtimesByMovieId(movieId);

        return ApiResponse.<List<ShowtimeResponse>>builder()
                .result(showtimes)
                .build();
    }

}
