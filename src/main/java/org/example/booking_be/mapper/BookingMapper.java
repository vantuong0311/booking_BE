package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.BookingRequest;
import org.example.booking_be.dto.responce.BookingResponse;
import org.example.booking_be.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    // Tạo Booking từ request (chỉ mapping những trường cần thiết)
    @Mapping(target = "id", ignore = true)          // id do DB sinh ra
    @Mapping(target = "status", ignore = true)      // set trong service
    @Mapping(target = "totalAmount", ignore = true) // tính trong service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "expiredAt", ignore = true)
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "showtimeId", ignore = true)  // set trong service
    Booking toBooking(BookingRequest request);

    // Mapping Booking → Response
    BookingResponse toResponse(Booking booking);

    // Cập nhật Booking (ví dụ muốn update status, paymentId)
    void updateBooking(BookingRequest request, @MappingTarget Booking booking);
}

