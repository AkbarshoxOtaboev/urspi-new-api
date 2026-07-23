package uz.urspi.newurspi.semester.controller;

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
import uz.urspi.newurspi.semester.dto.SemesterDTO;
import uz.urspi.newurspi.semester.response.SemesterListResponseApi;
import uz.urspi.newurspi.semester.response.SemesterResponse;
import uz.urspi.newurspi.semester.response.SemesterResponseApi;
import uz.urspi.newurspi.semester.service.SemesterService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
@Tag(name = "Semester rest api management controller")
public class SemesterController {
    private final SemesterService service;

    @PostMapping
    @PreAuthorize("hasAuthority('SEMESTER_CREATE')")
    @Operation(summary = "Semester create", description = "Only users with SEMESTER_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SemesterResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<SemesterResponse>> create(@Valid @RequestBody SemesterDTO dto) {
        SemesterResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<SemesterResponse>builder()
                        .message("Semester successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SEMESTER_VIEW')")
    @Operation(summary = "Fetch all semesters", description = "Only users with SEMESTER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SemesterListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<SemesterResponse>>> fetchAllSemesters() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<SemesterResponse>>builder()
                        .message("All semesters fetched success")
                        .data(service.fetchAllSemesters())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SEMESTER_VIEW')")
    @Operation(summary = "Fetch semester by id", description = "Only users with SEMESTER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SemesterResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<SemesterResponse>> findSemesterById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<SemesterResponse>builder()
                        .message("Semester found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SEMESTER_EDIT')")
    @Operation(summary = "Update semester", description = "Only users with SEMESTER_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SemesterResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<SemesterResponse>> update(@PathVariable Long id, @Valid @RequestBody SemesterDTO dto) {
        SemesterResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<SemesterResponse>builder()
                        .message("Semester successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SEMESTER_DELETE')")
    @Operation(summary = "Semester delete", description = "Only users with SEMESTER_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Semester successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('SEMESTER_EDIT')")
    @Operation(summary = "Active or Disable semester", description = "Only users with SEMESTER_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledSemester(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Semester status successfully changed").build()
        );
    }
}
