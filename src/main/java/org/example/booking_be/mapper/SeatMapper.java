package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.SeatCreateRequest;
import org.example.booking_be.dto.request.SeatUpdateRequest;
import org.example.booking_be.dto.responce.SeatResponse;
import org.example.booking_be.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    @Mapping(target = "id",ignore = true)
    Seat toSeat(SeatCreateRequest request);

    SeatResponse toSeatResponse(Seat seat);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "roomId",ignore = true)
    void updateSeat(SeatUpdateRequest request,
                    @MappingTarget Seat seat);

}
