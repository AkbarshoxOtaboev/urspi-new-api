package uz.urspi.newurspi.scientificarticle.controller;

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
import uz.urspi.newurspi.scientificarticle.dto.ScientificArticleDTO;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleListResponseApi;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponse;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponseApi;
import uz.urspi.newurspi.scientificarticle.service.ScientificArticleService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/scientific-articles")
@RequiredArgsConstructor
@Tag(name = "Scientific articles (Ilmiy maqolalar) rest api management")
public class ScientificArticleController {
    private final ScientificArticleService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCIENTIFIC_ARTICLE_CREATE')")
    @Operation(summary = "Create scientific article")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScientificArticleResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<ScientificArticleResponse>> create(
            @Valid @ModelAttribute ScientificArticleDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<ScientificArticleResponse>builder()
                        .message("Scientific article successfully created")
                        .data(service.create(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCIENTIFIC_ARTICLE_VIEW')")
    @Operation(summary = "Fetch all scientific articles")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScientificArticleListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<ScientificArticleResponse>>> fetchAll() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<ScientificArticleResponse>>builder()
                        .message("All scientific articles fetched success")
                        .data(service.fetchAll())
                        .build()
        );
    }

    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasAuthority('SCIENTIFIC_ARTICLE_VIEW')")
    @Operation(summary = "Fetch scientific articles by teacher id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScientificArticleListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<ScientificArticleResponse>>> fetchByTeacherId(
            @PathVariable Long teacherId
    ) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<ScientificArticleResponse>>builder()
                        .message("Teacher scientific articles fetched success")
                        .data(service.fetchByTeacherId(teacherId))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCIENTIFIC_ARTICLE_VIEW')")
    @Operation(summary = "Fetch scientific article by id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScientificArticleResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<ScientificArticleResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<ScientificArticleResponse>builder()
                        .message("Scientific article found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCIENTIFIC_ARTICLE_EDIT')")
    @Operation(summary = "Update scientific article")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScientificArticleResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<ScientificArticleResponse>> update(
            @PathVariable Long id,
            @Valid @ModelAttribute ScientificArticleDTO dto
    ) {
        return ResponseEntity.ok(
                RestApiResponse.<ScientificArticleResponse>builder()
                        .message("Scientific article successfully updated")
                        .data(service.update(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCIENTIFIC_ARTICLE_DELETE')")
    @Operation(summary = "Delete scientific article")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Scientific article successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('SCIENTIFIC_ARTICLE_EDIT')")
    @Operation(summary = "Active or Disable scientific article")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabled(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Scientific article status successfully changed").build()
        );
    }
}
