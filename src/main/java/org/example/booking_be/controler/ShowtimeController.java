package org.example.booking_be.controler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.ShowTimeCreateRequest;
import org.example.booking_be.dto.request.ShowtimeUpdateRequest;
import org.example.booking_be.dto.responce.SeatResponse;
import org.example.booking_be.dto.responce.ShowtimeResponse;
import org.example.booking_be.entity.Showtime;
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
}
