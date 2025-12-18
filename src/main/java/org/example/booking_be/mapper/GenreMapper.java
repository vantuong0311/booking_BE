package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.GenreCreateRequest;
import org.example.booking_be.dto.request.GenreUpdateRequest;
import org.example.booking_be.dto.responce.GenreResponse;
import org.example.booking_be.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    // Tạo entity từ create request
    Genre toGenre(GenreCreateRequest request);

    // Cập nhật entity từ update request
    void updateGenre(GenreUpdateRequest request, @MappingTarget Genre genre);

    // Entity → response
    GenreResponse toGenreResponse(Genre genre);
}
