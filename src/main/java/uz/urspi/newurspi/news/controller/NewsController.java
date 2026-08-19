package uz.urspi.newurspi.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.urspi.newurspi.news.dto.NewsDTO;
import uz.urspi.newurspi.news.response.*;
import uz.urspi.newurspi.news.service.NewsService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "News rest api management controller")
public class NewsController {
    private final NewsService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('NEWS_CREATE')")
    @Operation(summary = "News create", description = "Only users with NEWS_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NewsResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<NewsResponse>> create(@Valid @ModelAttribute NewsDTO dto) {
        NewsResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<NewsResponse>builder()
                        .message("News successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NEWS_VIEW')")
    @Operation(summary = "Fetch all news", description = "Only users with NEWS_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NewsListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<NewsResponse>>> fetchAllNews() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<NewsResponse>>builder()
                        .message("All news fetched success")
                        .data(service.fetchAllNews())
                        .build()
        );
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('NEWS_VIEW')")
    @Operation(summary = "Fetch all news by language", description = "Only users with NEWS_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NewsLocalizedListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<NewsLocalizedResponse>>> fetchAllNewsByLang(@PathVariable Language lang) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<NewsLocalizedResponse>>builder()
                        .message("All news fetched success")
                        .data(service.fetchAllNewsByLang(lang))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NEWS_VIEW')")
    @Operation(summary = "Fetch news by id", description = "Only users with NEWS_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NewsResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<NewsResponse>> findNewsById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<NewsResponse>builder()
                        .message("News found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('NEWS_EDIT')")
    @Operation(summary = "Update news", description = "Only users with NEWS_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NewsResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<NewsResponse>> update(@PathVariable Long id, @Valid @ModelAttribute NewsDTO dto) {
        NewsResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<NewsResponse>builder()
                        .message("News successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NEWS_DELETE')")
    @Operation(summary = "News delete", description = "Only users with NEWS_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("News successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('NEWS_EDIT')")
    @Operation(summary = "Active or Disable news", description = "Only users with NEWS_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledNews(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("News status successfully changed").build()
        );
    }
}
