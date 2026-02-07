package org.example.booking_be.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_be.enums.MovieStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "movies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
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
