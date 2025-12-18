package org.example.booking_be.controler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.CinemaCreateRequest;
import org.example.booking_be.dto.request.CinemaUpdateRequest;
import org.example.booking_be.dto.responce.CinemaResponse;
import org.example.booking_be.service.CinemaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinemas")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaController {
    CinemaService cinemaService;
    @GetMapping
    public ApiResponse<List<CinemaResponse>> getAllCinema(){
        List<CinemaResponse> cinemas = cinemaService.getAllCinema();
        return ApiResponse.<List<CinemaResponse>>builder()
                .result(cinemas)
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<CinemaResponse> getCinemaById(@PathVariable String id){
        CinemaResponse cinema = cinemaService.getCinemaById(id);
        return ApiResponse.<CinemaResponse>builder().result(cinema).build();
    }

    @PostMapping
    public ApiResponse<CinemaResponse> createCinema(CinemaCreateRequest request){
        CinemaResponse cinema = cinemaService.createCinema(request);
        return ApiResponse.<CinemaResponse>builder().result(cinema).build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCinema(@PathVariable String id){
        cinemaService.deleteCinema(id);
        return ApiResponse.<Void>builder().result(null).build();
    }
    @PutMapping("/{id}")
    public ApiResponse<CinemaResponse> updateCinema(@PathVariable String id, CinemaUpdateRequest request){
        CinemaResponse cinema = cinemaService.updateCinema(id, request);
        return ApiResponse.<CinemaResponse>builder().result(cinema).build();
    }
}
