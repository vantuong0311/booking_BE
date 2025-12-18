package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.CinemaCreateRequest;
import org.example.booking_be.dto.request.CinemaUpdateRequest;
import org.example.booking_be.dto.responce.CinemaResponse;
import org.example.booking_be.entity.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface CinemaMapper {
    @Mapping(target = "id",ignore = true)
    Cinema toCinema(CinemaCreateRequest cinemaCreateRequest);

    CinemaResponse toCinemaResponse(Cinema cinema);

    @Mapping(target = "id", ignore = true)
    void updateCinema(CinemaUpdateRequest request,@MappingTarget Cinema cinema);

}
