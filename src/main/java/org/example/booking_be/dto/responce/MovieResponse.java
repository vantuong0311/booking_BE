package org.example.booking_be.dto.responce;

import lombok.Data;
import org.example.booking_be.enums.MovieStatus;


import java.time.LocalDate;

@Data
public class MovieResponse {
    private String id;
    private String title;
    private String description;
    private int duration;
    private LocalDate releaseDate;
    private String posterUrl;
    private String posterPublicId;
    private String trailerUrl;
    private String genreId;
    private MovieStatus status;
}
