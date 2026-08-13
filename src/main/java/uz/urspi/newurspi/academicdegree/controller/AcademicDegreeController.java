package uz.urspi.newurspi.academicdegree.controller;

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
import uz.urspi.newurspi.academicdegree.dto.AcademicDegreeDTO;
import uz.urspi.newurspi.academicdegree.response.AcademicDegreeListResponseApi;
import uz.urspi.newurspi.academicdegree.response.AcademicDegreeResponse;
import uz.urspi.newurspi.academicdegree.response.AcademicDegreeResponseApi;
import uz.urspi.newurspi.academicdegree.service.AcademicDegreeService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/academic-degrees")
@RequiredArgsConstructor
@Tag(name = "Academic degree (Ilmiy daraja) rest api management controller")
public class AcademicDegreeController {
    private final AcademicDegreeService service;

    @PostMapping
    @PreAuthorize("hasAuthority('ACADEMIC_DEGREE_CREATE')")
    @Operation(summary = "Create academic degree (ilmiy daraja)")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AcademicDegreeResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<AcademicDegreeResponse>> create(@Valid @RequestBody AcademicDegreeDTO dto) {
        AcademicDegreeResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<AcademicDegreeResponse>builder()
                        .message("Academic degree successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ACADEMIC_DEGREE_VIEW')")
    @Operation(summary = "Fetch all academic degrees")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AcademicDegreeListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<AcademicDegreeResponse>>> fetchAllAcademicDegrees() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<AcademicDegreeResponse>>builder()
                        .message("All academic degrees fetched success")
                        .data(service.fetchAllAcademicDegrees())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ACADEMIC_DEGREE_VIEW')")
    @Operation(summary = "Fetch academic degree by id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AcademicDegreeResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<AcademicDegreeResponse>> findAcademicDegreeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<AcademicDegreeResponse>builder()
                        .message("Academic degree found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ACADEMIC_DEGREE_EDIT')")
    @Operation(summary = "Update academic degree")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AcademicDegreeResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<AcademicDegreeResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody AcademicDegreeDTO dto
    ) {
        AcademicDegreeResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<AcademicDegreeResponse>builder()
                        .message("Academic degree successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ACADEMIC_DEGREE_DELETE')")
    @Operation(summary = "Delete academic degree")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Academic degree successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('ACADEMIC_DEGREE_EDIT')")
    @Operation(summary = "Active or Disable academic degree")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledAcademicDegree(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Academic degree status successfully changed").build()
        );
    }
}
