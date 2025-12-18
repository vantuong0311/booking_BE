package org.example.booking_be.service;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.request.MovieCreateRequest;
import org.example.booking_be.dto.request.MovieUpdateRequest;
import org.example.booking_be.dto.responce.MovieResponse;
import org.example.booking_be.entity.Movie;
import org.example.booking_be.enums.MovieStatus;
import org.example.booking_be.mapper.MovieMapper;
import org.example.booking_be.reponsitory.MovieReponsitory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieReponsitory movieRepo;
    private final MovieMapper movieMapper;

    // ADMIN
    public MovieResponse create(MovieCreateRequest request) {
        Movie movie = movieMapper.toMovie(request);
        return movieMapper.toMovieResponse(movieRepo.save(movie));
    }

    public MovieResponse update(String id, MovieUpdateRequest request) {
        Movie movie = movieRepo.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movieMapper.updateMovie(request, movie);
        return movieMapper.toMovieResponse(movieRepo.save(movie));
    }

    public void delete(String id) {
        movieRepo.deleteById(id);
    }

    public MovieResponse toggleStatus(String id) {
        Movie movie = movieRepo.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setStatus(movie.getStatus() == MovieStatus.SHOWING ? MovieStatus.STOPPED : MovieStatus.SHOWING);
        return movieMapper.toMovieResponse(movieRepo.save(movie));
    }

    // USER
    public MovieResponse getById(String id) {
        Movie movie = movieRepo.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        return movieMapper.toMovieResponse(movie);
    }

    public List<MovieResponse> getAll() {
        return movieRepo.findAll().stream().map(movieMapper::toMovieResponse).toList();
    }

    public List<MovieResponse> getShowing() {
        LocalDate now = LocalDate.now();
        return movieRepo.findAll().stream()
                .filter(m -> m.getStatus() == MovieStatus.SHOWING && !m.getReleaseDate().isAfter(now))
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    public List<MovieResponse> getComingSoon() {
        LocalDate now = LocalDate.now();
        return movieRepo.findAll().stream()
                .filter(m -> m.getStatus() == MovieStatus.SHOWING && m.getReleaseDate().isAfter(now))
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    public List<MovieResponse> searchByTitle(String title) {
        return movieRepo.findByTitleContainingIgnoreCase(title).stream()
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    public List<MovieResponse> filterByGenre(String genreId) {
        return movieRepo.findByGenreId(genreId).stream()
                .map(movieMapper::toMovieResponse)
                .toList();
    }
}
