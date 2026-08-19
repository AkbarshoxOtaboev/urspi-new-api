package uz.urspi.newurspi.announcement.controller;

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
import uz.urspi.newurspi.announcement.dto.AnnouncementDTO;
import uz.urspi.newurspi.announcement.response.AnnouncementListResponseApi;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedListResponseApi;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedResponse;
import uz.urspi.newurspi.announcement.response.AnnouncementResponse;
import uz.urspi.newurspi.announcement.response.AnnouncementResponseApi;
import uz.urspi.newurspi.announcement.service.AnnouncementService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Tag(name = "Announcement rest api management controller")
public class AnnouncementController {
    private final AnnouncementService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ANNOUNCEMENT_CREATE')")
    @Operation(summary = "Announcement create", description = "Only users with ANNOUNCEMENT_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<AnnouncementResponse>> create(@Valid @ModelAttribute AnnouncementDTO dto) {
        AnnouncementResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<AnnouncementResponse>builder()
                        .message("Announcement successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ANNOUNCEMENT_VIEW')")
    @Operation(summary = "Fetch all announcements", description = "Only users with ANNOUNCEMENT_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<AnnouncementResponse>>> fetchAllAnnouncements() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<AnnouncementResponse>>builder()
                        .message("All announcements fetched success")
                        .data(service.fetchAllAnnouncements())
                        .build()
        );
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('ANNOUNCEMENT_VIEW')")
    @Operation(summary = "Fetch all announcements by language", description = "Only users with ANNOUNCEMENT_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementLocalizedListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<AnnouncementLocalizedResponse>>> fetchAllAnnouncementsByLang(@PathVariable Language lang) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<AnnouncementLocalizedResponse>>builder()
                        .message("All announcements fetched success")
                        .data(service.fetchAllAnnouncementsByLang(lang))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ANNOUNCEMENT_VIEW')")
    @Operation(summary = "Fetch announcement by id", description = "Only users with ANNOUNCEMENT_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<AnnouncementResponse>> findAnnouncementById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<AnnouncementResponse>builder()
                        .message("Announcement found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ANNOUNCEMENT_EDIT')")
    @Operation(summary = "Update announcement", description = "Only users with ANNOUNCEMENT_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<AnnouncementResponse>> update(@PathVariable Long id, @Valid @ModelAttribute AnnouncementDTO dto) {
        AnnouncementResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<AnnouncementResponse>builder()
                        .message("Announcement successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ANNOUNCEMENT_DELETE')")
    @Operation(summary = "Announcement delete", description = "Only users with ANNOUNCEMENT_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Announcement successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('ANNOUNCEMENT_EDIT')")
    @Operation(summary = "Active or Disable announcement", description = "Only users with ANNOUNCEMENT_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledAnnouncement(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Announcement status successfully changed").build()
        );
    }
}
