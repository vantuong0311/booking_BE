package org.example.booking_be.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.dto.request.CinemaCreateRequest;
import org.example.booking_be.dto.request.CinemaUpdateRequest;
import org.example.booking_be.dto.responce.CinemaResponse;
import org.example.booking_be.entity.Cinema;
import org.example.booking_be.enums.ErrorCode;
import org.example.booking_be.exception.AppException;
import org.example.booking_be.mapper.CinemaMapper;
import org.example.booking_be.reponsitory.CinemaReponsitory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class CinemaService {
    CinemaReponsitory cinemaReponsitory;
    CinemaMapper cinemaMapper;

    public List<CinemaResponse> getAllCinema(){
        List<Cinema> cinemas = cinemaReponsitory.findAll();
        return cinemas.stream().map(cinemaMapper:: toCinemaResponse)
                .collect(Collectors.toList());
    }

    public CinemaResponse getCinemaById(String id){
        Cinema cinema = cinemaReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        return cinemaMapper.toCinemaResponse(cinema);

    }
    public CinemaResponse createCinema(CinemaCreateRequest request){
        Cinema cinema = cinemaMapper.toCinema(request);
        cinemaReponsitory.save(cinema);
        return cinemaMapper.toCinemaResponse(cinema);
    }
    public CinemaResponse updateCinema(String id, CinemaUpdateRequest request){
        Cinema cinema = cinemaReponsitory.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        cinemaMapper.updateCinema(request,cinema);
        cinemaReponsitory.save(cinema);
        return cinemaMapper.toCinemaResponse(cinema);

    }
    public void deleteCinema(String id){
        Cinema cinema= cinemaReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        cinemaReponsitory.delete(cinema);

    }

}
