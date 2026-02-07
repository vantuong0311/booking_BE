package org.example.booking_be.dto.request;

import lombok.Data;
import org.example.booking_be.enums.MovieStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class MovieUpdateRequest {
    private String title;
    private String description;
    private int duration;
    private LocalDate releaseDate;

    private MultipartFile posterUrl;
    private String posterPublicId;
    private String trailerUrl;

    private String genreId;
    private MovieStatus status;
}
