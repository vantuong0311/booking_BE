package org.example.booking_be.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_be.enums.MovieStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")

public class Movie {
    @Id
    private String id;

    private String title;
    private String description;
    private int duration; // phút
    private LocalDate releaseDate;

    private String posterUrl; // Cloudinary URL

    private MovieStatus status;
}
