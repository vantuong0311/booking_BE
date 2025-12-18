package org.example.booking_be.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.request.ShowTimeCreateRequest;
import org.example.booking_be.dto.request.ShowtimeUpdateRequest;
import org.example.booking_be.dto.responce.ShowtimeResponse;
import org.example.booking_be.entity.HoldingSeat;
import org.example.booking_be.entity.Showtime;
import org.example.booking_be.enums.ErrorCode;
import org.example.booking_be.exception.AppException;
import org.example.booking_be.mapper.ShowtimeMapper;
import org.example.booking_be.reponsitory.ShowtimeReponsitory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowTimeService {
    ShowtimeReponsitory showtimeReponsitory;
    ShowtimeMapper showtimeMapper;
    public ShowtimeResponse createShowTime(ShowTimeCreateRequest request){
        Showtime showtime = showtimeMapper.toShowTime(request);
        showtime.setBookedSeats(new ArrayList<>());
        showtime.setHoldingSeats(new ArrayList<>());
        Showtime saved = showtimeReponsitory.save(showtime);
        return showtimeMapper.toResponse(saved);
    }
    public List<ShowtimeResponse> getAllShowTimes(){
        List<Showtime> showtime = showtimeReponsitory.findAll();
        return showtime.stream().map(showtimeMapper::toResponse)
                .collect(Collectors.toList());

    }
    public ShowtimeResponse getShowtimeById(String id){
        Showtime showtime = showtimeReponsitory.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        return showtimeMapper.toResponse(showtime);
    }
    public ShowtimeResponse updateShowTime(String id, ShowtimeUpdateRequest request){
        Showtime showtime = showtimeReponsitory.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        showtimeMapper.updateShowtime(request,showtime);
        showtimeReponsitory.save(showtime);
        return showtimeMapper.toResponse(showtime);
    }
    public void delete(String id){
        Showtime showtime = showtimeReponsitory.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        showtimeReponsitory.delete(showtime);
    }
    /* ================= HOLD SEAT ================= */
    public void holdSeats(
            String showtimeId,
            List<String> seatCodes,
            String userId
    ) {
        Showtime showtime = getShowtime(showtimeId);

        removeExpiredHoldingSeats(showtime);

        checkBookedSeats(showtime, seatCodes);
        checkHoldingSeats(showtime, seatCodes);

        LocalDateTime holdUntil = LocalDateTime.now().plusMinutes(5);

        for (String seat : seatCodes) {
            showtime.getHoldingSeats()
                    .add(new HoldingSeat(seat, userId, holdUntil));
        }

        showtimeReponsitory.save(showtime);
    }

    /* ================= CONFIRM BOOKING ================= */

    public void confirmBooking(
            String showtimeId,
            List<String> seatCodes,
            String userId
    ) {
        Showtime showtime = getShowtime(showtimeId);

        removeExpiredHoldingSeats(showtime);

        // chỉ cho thanh toán ghế user đang giữ
        for (String seat : seatCodes) {
            boolean valid = showtime.getHoldingSeats().stream()
                    .anyMatch(h ->
                            h.getSeatCode().equals(seat)
                                    && h.getUserId().equals(userId)
                    );

            if (!valid) {
                throw new AppException(ErrorCode.SEAT_NOT_HELD);
            }
        }

        // chuyển sang booked
        showtime.getBookedSeats().addAll(seatCodes);

        // xoá holding seat
        showtime.getHoldingSeats()
                .removeIf(h ->
                        seatCodes.contains(h.getSeatCode())
                                && h.getUserId().equals(userId)
                );

        showtimeReponsitory.save(showtime);
    }

    /* ================= PRIVATE METHODS ================= */

    private Showtime getShowtime(String id) {
        return showtimeReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }

    private void checkBookedSeats(Showtime showtime, List<String> seatCodes) {
        for (String seat : seatCodes) {
            if (showtime.getBookedSeats().contains(seat)) {
                throw new AppException(ErrorCode.SEAT_ALREADY_BOOKED);
            }
        }
    }

    private void checkHoldingSeats(Showtime showtime, List<String> seatCodes) {
        for (HoldingSeat holding : showtime.getHoldingSeats()) {
            if (seatCodes.contains(holding.getSeatCode())) {
                throw new AppException(ErrorCode.SEAT_ALREADY_HELD);
            }
        }
    }

    private void removeExpiredHoldingSeats(Showtime showtime) {
        LocalDateTime now = LocalDateTime.now();
        showtime.getHoldingSeats()
                .removeIf(h -> h.getHoldUntil().isBefore(now));
    }


}
