package org.example.booking_be.dto.request;

import lombok.Data;
import org.example.booking_be.enums.MovieStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class MovieCreateRequest {
    private String title;
    private String description;
    private int duration;
    private LocalDate releaseDate;

    private MultipartFile posterUrl;
    private String trailerUrl;
    private String posterPublicId;

    private String genreId; // Chỉ gửi genreId
    private MovieStatus status;
}
