package org.example.booking_be.controler;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.ApiResponse;
import org.example.booking_be.dto.request.MovieCreateRequest;
import org.example.booking_be.dto.request.MovieUpdateRequest;
import org.example.booking_be.dto.responce.MovieResponse;
import org.example.booking_be.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // ADMIN
    @PostMapping
    public ApiResponse<MovieResponse> create(@RequestBody MovieCreateRequest request){
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.create(request))
                .message("Create movie successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MovieResponse> update(@PathVariable String id,
                                             @RequestBody MovieUpdateRequest request){
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.update(id, request))
                .message("Update movie successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id){
        movieService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete movie successfully")
                .build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ApiResponse<MovieResponse> toggleStatus(@PathVariable String id){
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.toggleStatus(id))
                .message("Toggle movie status successfully")
                .build();
    }

    // USER
    @GetMapping
    public ApiResponse<List<MovieResponse>> getAll(){
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.getAll())
                .build();
    }

    @GetMapping("/showing")
    public ApiResponse<List<MovieResponse>> getShowing(){
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.getShowing())
                .build();
    }

    @GetMapping("/coming-soon")
    public ApiResponse<List<MovieResponse>> getComingSoon(){
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.getComingSoon())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MovieResponse> getById(@PathVariable String id){
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.getById(id))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<MovieResponse>> search(@RequestParam String title){
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.searchByTitle(title))
                .build();
    }

    @GetMapping("/filter")
    public ApiResponse<List<MovieResponse>> filter(@RequestParam String genreId){
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.filterByGenre(genreId))
                .build();
    }
}
