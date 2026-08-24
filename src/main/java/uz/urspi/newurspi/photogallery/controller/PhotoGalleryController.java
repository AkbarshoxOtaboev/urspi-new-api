package uz.urspi.newurspi.photogallery.controller;

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
import uz.urspi.newurspi.photogallery.dto.PhotoGalleryDTO;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryListResponseApi;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryLocalizedListResponseApi;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryLocalizedResponse;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryResponse;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryResponseApi;
import uz.urspi.newurspi.photogallery.service.PhotoGalleryService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/photo-galleries")
@RequiredArgsConstructor
@Tag(name = "Photo gallery rest api management")
public class PhotoGalleryController {
    private final PhotoGalleryService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('PHOTO_GALLERY_CREATE')")
    @Operation(summary = "Photo gallery create")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = PhotoGalleryResponseApi.class)))
    public ResponseEntity<RestApiResponse<PhotoGalleryResponse>> create(@Valid @ModelAttribute PhotoGalleryDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<PhotoGalleryResponse>builder()
                        .message("Photo gallery successfully created")
                        .data(service.create(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PHOTO_GALLERY_VIEW')")
    @Operation(summary = "Fetch all photo galleries")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PhotoGalleryListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<PhotoGalleryResponse>>> fetchAll() {
        return ResponseEntity.ok(RestApiResponse.<List<PhotoGalleryResponse>>builder()
                .message("All photo galleries fetched success")
                .data(service.fetchAll())
                .build());
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('PHOTO_GALLERY_VIEW')")
    @Operation(summary = "Fetch photo galleries by language")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PhotoGalleryLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<PhotoGalleryLocalizedResponse>>> fetchAllByLang(@PathVariable Language lang) {
        return ResponseEntity.ok(RestApiResponse.<List<PhotoGalleryLocalizedResponse>>builder()
                .message("All photo galleries fetched success")
                .data(service.fetchAllByLang(lang))
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PHOTO_GALLERY_VIEW')")
    @Operation(summary = "Fetch photo gallery by id")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PhotoGalleryResponseApi.class)))
    public ResponseEntity<RestApiResponse<PhotoGalleryResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<PhotoGalleryResponse>builder()
                .message("Photo gallery found success")
                .data(service.findById(id))
                .build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('PHOTO_GALLERY_EDIT')")
    @Operation(summary = "Update photo gallery")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PhotoGalleryResponseApi.class)))
    public ResponseEntity<RestApiResponse<PhotoGalleryResponse>> update(@PathVariable Long id, @Valid @ModelAttribute PhotoGalleryDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<PhotoGalleryResponse>builder()
                .message("Photo gallery successfully updated")
                .data(service.update(id, dto))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PHOTO_GALLERY_DELETE')")
    @Operation(summary = "Delete photo gallery")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Photo gallery successfully deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('PHOTO_GALLERY_EDIT')")
    @Operation(summary = "Active or disable photo gallery")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabled(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Photo gallery status successfully changed").build());
    }
}
