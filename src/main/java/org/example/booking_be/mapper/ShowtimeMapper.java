package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.ShowTimeCreateRequest;
import org.example.booking_be.dto.request.ShowtimeUpdateRequest;
import org.example.booking_be.dto.responce.ShowtimeResponse;
import org.example.booking_be.entity.Showtime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface ShowtimeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookedSeats", ignore = true)
    @Mapping(target = "holdingSeats", ignore = true)
    Showtime toShowTime(ShowTimeCreateRequest request);
//    @Mapping(target = "holdingSeats", ignore = true)
    ShowtimeResponse toResponse(Showtime showtime);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movieId", ignore = true)
    @Mapping(target = "roomId", ignore = true)
    @Mapping(target = "bookedSeats", ignore = true)
    @Mapping(target = "holdingSeats", ignore = true)
    void updateShowtime(ShowtimeUpdateRequest request,
                        @MappingTarget Showtime showtime);


}
