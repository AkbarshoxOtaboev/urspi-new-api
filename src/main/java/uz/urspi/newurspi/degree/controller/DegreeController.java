package uz.urspi.newurspi.degree.controller;

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
import uz.urspi.newurspi.degree.dto.DegreeDTO;
import uz.urspi.newurspi.degree.response.DegreeListResponseApi;
import uz.urspi.newurspi.degree.response.DegreeResponse;
import uz.urspi.newurspi.degree.response.DegreeResponseApi;
import uz.urspi.newurspi.degree.service.DegreeService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/degrees")
@RequiredArgsConstructor
@Tag(name = "Degree rest api management controller")
public class DegreeController {
    private final DegreeService service;

    @PostMapping
    @PreAuthorize("hasAuthority('DEGREE_CREATE')")
    @Operation(summary = "Degree create", description = "Only users with DEGREE_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DegreeResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<DegreeResponse>> create(@Valid @RequestBody DegreeDTO dto) {
        DegreeResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<DegreeResponse>builder()
                        .message("Degree successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DEGREE_VIEW')")
    @Operation(summary = "Fetch all degrees", description = "Only users with DEGREE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DegreeListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<DegreeResponse>>> fetchAllDegrees() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<DegreeResponse>>builder()
                        .message("All degrees fetched success")
                        .data(service.fetchAllDegrees())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DEGREE_VIEW')")
    @Operation(summary = "Fetch degree by id", description = "Only users with DEGREE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DegreeResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<DegreeResponse>> findDegreeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<DegreeResponse>builder()
                        .message("Degree found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DEGREE_EDIT')")
    @Operation(summary = "Update degree", description = "Only users with DEGREE_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DegreeResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<DegreeResponse>> update(@PathVariable Long id, @Valid @RequestBody DegreeDTO dto) {
        DegreeResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<DegreeResponse>builder()
                        .message("Degree successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DEGREE_DELETE')")
    @Operation(summary = "Degree delete", description = "Only users with DEGREE_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Degree successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('DEGREE_EDIT')")
    @Operation(summary = "Active or Disable degree", description = "Only users with DEGREE_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledDegree(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Degree status successfully changed").build()
        );
    }
}
