package uz.urspi.newurspi.center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.urspi.newurspi.center.dto.CenterDTO;
import uz.urspi.newurspi.center.response.*;
import uz.urspi.newurspi.center.service.CenterService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
@RequiredArgsConstructor
@Tag(name = "Center rest api management controller")
public class CenterController {
    private final CenterService service;

    @PostMapping
    @PreAuthorize("hasAuthority('CENTER_CREATE')")
    @Operation(summary = "Center create", description = "Only users with CENTER_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CenterResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<CenterResponse>> create(@Valid @RequestBody CenterDTO dto) {
        CenterResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<CenterResponse>builder()
                        .message("Center successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CENTER_VIEW')")
    @Operation(summary = "Fetch all centers", description = "Only users with CENTER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CenterListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<CenterResponse>>> fetchAllCenters() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<CenterResponse>>builder()
                        .message("All centers fetched success")
                        .data(service.fetchAllCenters())
                        .build()
        );
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('CENTER_VIEW')")
    @Operation(summary = "Fetch all centers by language", description = "Only users with CENTER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CenterLocalizedListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<CenterLocalizedResponse>>> fetchAllCentersByLang(@PathVariable Language lang) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<CenterLocalizedResponse>>builder()
                        .message("All centers fetched success")
                        .data(service.fetchAllCentersByLang(lang))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CENTER_VIEW')")
    @Operation(summary = "Fetch center by id", description = "Only users with CENTER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CenterResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<CenterResponse>> findCenterById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<CenterResponse>builder()
                        .message("Center found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CENTER_EDIT')")
    @Operation(summary = "Update center", description = "Only users with CENTER_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CenterResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<CenterResponse>> update(@PathVariable Long id, @Valid @RequestBody CenterDTO dto) {
        CenterResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<CenterResponse>builder()
                        .message("Center successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CENTER_DELETE')")
    @Operation(summary = "Center delete", description = "Only users with CENTER_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Center successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('CENTER_EDIT')")
    @Operation(summary = "Active or Disable center", description = "Only users with CENTER_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledCenter(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Center status successfully changed").build()
        );
    }
}
