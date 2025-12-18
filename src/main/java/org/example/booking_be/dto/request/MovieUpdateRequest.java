package org.example.booking_be.dto.request;

import lombok.Data;
import org.example.booking_be.enums.MovieStatus;

import java.time.LocalDate;

@Data
public class MovieUpdateRequest {
    private String title;
    private String description;
    private int duration;
    private LocalDate releaseDate;

    private String posterUrl;
    private String trailerUrl;

    private String genreId;
    private MovieStatus status;
}
