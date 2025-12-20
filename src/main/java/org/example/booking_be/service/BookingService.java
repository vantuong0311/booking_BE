package org.example.booking_be.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.request.BookingRequest;
import org.example.booking_be.dto.responce.BookingResponse;
import org.example.booking_be.entity.Booking;
import org.example.booking_be.entity.Payment;
import org.example.booking_be.entity.Showtime;
import org.example.booking_be.enums.BookingStatus;
import org.example.booking_be.enums.ErrorCode;
import org.example.booking_be.enums.PaymentMethod;
import org.example.booking_be.enums.PaymentStatus;
import org.example.booking_be.exception.AppException;
import org.example.booking_be.mapper.BookingMapper;
import org.example.booking_be.reponsitory.BookingReponsitory;
import org.example.booking_be.reponsitory.PaymentReponsitory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService {

    BookingReponsitory bookingRepository;
    ShowTimeService showTimeService; // để lấy showtime & kiểm tra ghế
    BookingMapper bookingMapper;
    PaymentReponsitory paymentRepository;

    // ================== CREATE BOOKING ==================
    public BookingResponse createBooking(String showtimeId, BookingRequest request) {
        Showtime showtime = showTimeService.getShowtimeByIdRaw(showtimeId);

        // Kiểm tra ghế còn hold của user
        LocalDateTime now = LocalDateTime.now();
        for (String seat : request.getSeats()) {
            boolean held = showtime.getHoldingSeats().stream()
                    .anyMatch(h -> h.getSeatCode().equals(seat)
                            && h.getHoldUntil().isAfter(now)
                            && h.getUserId().equals(request.getUserId()));
            if (!held) {
                throw new AppException(ErrorCode.SEAT_NOT_HELD);
            }
        }

        Booking booking = bookingMapper.toBooking(request);
        booking.setShowtimeId(showtimeId);
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(now);
        booking.setExpiredAt(now.plusMinutes(15)); // 15 phút để thanh toán
        booking.setTotalAmount(request.getSeats().size() * showtime.getPrice());

        Booking saved = bookingRepository.save(booking);

        return bookingMapper.toResponse(saved);
    }

    // ================== CONFIRM BOOKING ==================
    public BookingResponse confirmBooking(String bookingId, String paymentId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // 1️⃣ Tạo payment thực sự
        Payment payment = Payment.builder()
                .bookingId(bookingId)
                .amount(booking.getTotalAmount())
                .status(PaymentStatus.SUCCESS)
                .method(PaymentMethod.CASH) // hoặc lấy từ request nếu bạn có
                .paymentTime(LocalDateTime.now())
                .id(paymentId) // dùng paymentId client gửi hoặc generate
                .build();
        paymentRepository.save(payment);

        // 2️⃣ Cập nhật booking
        booking.setStatus(BookingStatus.PAID);
        booking.setPaymentId(paymentId);
        booking.setExpiredAt(null);
        bookingRepository.save(booking);

        // 3️⃣ Đồng bộ ghế sang booked
        showTimeService.confirmBooking(booking.getShowtimeId(), booking.getSeats(), booking.getUserId());

        return bookingMapper.toResponse(booking);
    }

    // ================== CANCEL BOOKING ==================
    public BookingResponse cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // Cập nhật trạng thái
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setExpiredAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);

        // Giải phóng ghế hold
        showTimeService.releaseSeats(booking.getShowtimeId(), booking.getUserId());

        return bookingMapper.toResponse(saved);
    }

    // ================== HANDLE EXPIRED BOOKINGS ==================
    public void expireBookings() {
        LocalDateTime now = LocalDateTime.now();
        List<Booking> expiredBookings = bookingRepository.findByStatusAndExpiredAtBefore(
                BookingStatus.PENDING, now);

        for (Booking booking : expiredBookings) {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);

            // Giải phóng ghế hold
            showTimeService.releaseSeats(booking.getShowtimeId(), booking.getUserId());
        }
    }

    // ================== GET BOOKINGS ==================
    public List<BookingResponse> getBookingsByUser(String userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookingsByShowtime(String showtimeId) {
        return bookingRepository.findByShowtimeId(showtimeId).stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return bookingMapper.toResponse(booking);
    }
}
