package org.example.booking_be.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.request.SeatCreateRequest;
import org.example.booking_be.dto.request.SeatUpdateRequest;
import org.example.booking_be.dto.responce.SeatResponse;
import org.example.booking_be.entity.Seat;
import org.example.booking_be.enums.ErrorCode;
import org.example.booking_be.exception.AppException;
import org.example.booking_be.mapper.SeatMapper;
import org.example.booking_be.reponsitory.SeatReponsitory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SeatService {
    SeatReponsitory seatReponsitory;
    SeatMapper seatMapper;
    public List<SeatResponse> getAllSeats(){
        List<Seat> seats = seatReponsitory.findAll();
        return seats.stream()
                .map(seatMapper::toSeatResponse)
                .collect(Collectors.toList());
    }

    public SeatResponse getSeatById(String id){
        Seat seat = seatReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        return seatMapper.toSeatResponse(seat);
    }

    public SeatResponse updateSeat(String id, SeatUpdateRequest request){
        Seat seat = seatReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        seatMapper.updateSeat(request,seat);
        seatReponsitory.save(seat);
        return seatMapper.toSeatResponse(seat);
    }
    public SeatResponse createSeat(SeatCreateRequest request){
        Seat seat = seatMapper.toSeat(request);
        seatReponsitory.save(seat);
        return seatMapper.toSeatResponse(seat);

    }
    public void deleteSeat(String id){
        Seat seat = seatReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        seatReponsitory.delete(seat);
    }

}
