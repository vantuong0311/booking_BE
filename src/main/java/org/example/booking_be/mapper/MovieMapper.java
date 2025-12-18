package org.example.booking_be.mapper;

import org.example.booking_be.dto.request.MovieCreateRequest;
import org.example.booking_be.dto.request.MovieUpdateRequest;
import org.example.booking_be.dto.responce.MovieResponse;
import org.example.booking_be.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "id", ignore = true)
    Movie toMovie(MovieCreateRequest request);

    MovieResponse toMovieResponse(Movie movie);

    @Mapping(target = "id", ignore = true)
    void updateMovie(MovieUpdateRequest request, @MappingTarget Movie movie);
}
