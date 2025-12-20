package org.example.booking_be.controler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.BookingRequest;
import org.example.booking_be.dto.responce.BookingResponse;
import org.example.booking_be.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {

    BookingService bookingService;

    @PostMapping
    public ApiResponse<BookingResponse> createBooking(
            @RequestParam String showtimeId,
            @RequestBody BookingRequest request) {
        BookingResponse booking = bookingService.createBooking(showtimeId, request);
        return ApiResponse.<BookingResponse>builder().result(booking).build();
    }

    @PostMapping("/{id}/confirm")
    public ApiResponse<BookingResponse> confirmBooking(
            @PathVariable String id,
            @RequestParam String paymentId) {
        BookingResponse booking = bookingService.confirmBooking(id, paymentId);
        return ApiResponse.<BookingResponse>builder().result(booking).build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<BookingResponse>> getByUser(@PathVariable String userId) {
        List<BookingResponse> bookings = bookingService.getBookingsByUser(userId);
        return ApiResponse.<List<BookingResponse>>builder().result(bookings).build();
    }

    @GetMapping("/showtime/{showtimeId}")
    public ApiResponse<List<BookingResponse>> getByShowtime(@PathVariable String showtimeId) {
        List<BookingResponse> bookings = bookingService.getBookingsByShowtime(showtimeId);
        return ApiResponse.<List<BookingResponse>>builder().result(bookings).build();
    }
}
