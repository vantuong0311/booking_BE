package org.example.booking_be.service;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.request.GenreCreateRequest;
import org.example.booking_be.dto.request.GenreUpdateRequest;
import org.example.booking_be.dto.responce.GenreResponse;
import org.example.booking_be.entity.Genre;
import org.example.booking_be.mapper.GenreMapper;
import org.example.booking_be.reponsitory.GenreRepository;
import org.example.booking_be.reponsitory.MovieReponsitory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepo;
    private final MovieReponsitory movieRepo;
    private final GenreMapper genreMapper;

    public List<GenreResponse> getAllGenres() {
        return genreRepo.findAll().stream()
                .map(genreMapper::toGenreResponse)
                .toList();
    }

    public GenreResponse addGenre(GenreCreateRequest request) {
        Genre genre = genreMapper.toGenre(request);
        genre = genreRepo.save(genre);
        return genreMapper.toGenreResponse(genre);
    }

    public GenreResponse updateGenre(String id, GenreUpdateRequest request) {
        Genre genre = genreRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        genreMapper.updateGenre(request, genre);
        genre = genreRepo.save(genre);
        return genreMapper.toGenreResponse(genre);
    }

    public void deleteGenre(String id) {
        boolean exists = movieRepo.findAll().stream()
                .anyMatch(m -> m.getGenreId() != null && m.getGenreId().equals(id));
        if (exists) throw new RuntimeException("Cannot delete genre: still used by some movies");
        genreRepo.deleteById(id);
    }

}

