package org.example.booking_be.controler;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.GenreCreateRequest;
import org.example.booking_be.dto.request.GenreUpdateRequest;
import org.example.booking_be.dto.responce.GenreResponse;
import org.example.booking_be.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    // Lấy danh sách thể loại
    @GetMapping
    public ApiResponse<List<GenreResponse>> getAllGenres() {
        List<GenreResponse> genres = genreService.getAllGenres();
        return ApiResponse.<List<GenreResponse>>builder()
                .code(1000)
                .message("Success")
                .result(genres)
                .build();
    }

    // Thêm thể loại
    @PostMapping
    public ApiResponse<GenreResponse> addGenre(@RequestBody GenreCreateRequest request) {
        GenreResponse genre = genreService.addGenre(request);
        return ApiResponse.<GenreResponse>builder()
                .code(1000)
                .message("Genre created successfully")
                .result(genre)
                .build();
    }

    // Cập nhật thể loại
    @PutMapping("/{id}")
    public ApiResponse<GenreResponse> updateGenre(@PathVariable String id,
                                                  @RequestBody GenreUpdateRequest request) {
        GenreResponse genre = genreService.updateGenre(id, request);
        return ApiResponse.<GenreResponse>builder()
                .code(1000)
                .message("Genre updated successfully")
                .result(genre)
                .build();
    }

    // Xóa thể loại
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteGenre(@PathVariable String id) {
        genreService.deleteGenre(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Genre deleted successfully")
                .result(null)
                .build();
    }
}
