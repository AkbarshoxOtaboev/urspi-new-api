package uz.urspi.newurspi.study_year.controller;

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
import uz.urspi.newurspi.study_year.dto.StudyYearDTO;
import uz.urspi.newurspi.study_year.response.StudyYearListResponseApi;
import uz.urspi.newurspi.study_year.response.StudyYearResponse;
import uz.urspi.newurspi.study_year.response.StudyYearResponseApi;
import uz.urspi.newurspi.study_year.service.StudyYearService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/study-years")
@RequiredArgsConstructor
@Tag(name = "Study year rest api management controller")
public class StudyYearController {
    private final StudyYearService service;

    @PostMapping
    @PreAuthorize("hasAuthority('STUDY_YEAR_CREATE')")
    @Operation(summary = "Study year create", description = "Only users with STUDY_YEAR_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudyYearResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<StudyYearResponse>> create(@Valid @RequestBody StudyYearDTO dto) {
        StudyYearResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<StudyYearResponse>builder()
                        .message("Study year successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('STUDY_YEAR_VIEW')")
    @Operation(summary = "Fetch all study years", description = "Only users with STUDY_YEAR_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudyYearListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<StudyYearResponse>>> fetchAllStudyYears() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<StudyYearResponse>>builder()
                        .message("All study years fetched success")
                        .data(service.fetchAllStudyYears())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('STUDY_YEAR_VIEW')")
    @Operation(summary = "Fetch study year by id", description = "Only users with STUDY_YEAR_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudyYearResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<StudyYearResponse>> findStudyYearById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<StudyYearResponse>builder()
                        .message("Study year found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('STUDY_YEAR_EDIT')")
    @Operation(summary = "Update study year", description = "Only users with STUDY_YEAR_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudyYearResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<StudyYearResponse>> update(@PathVariable Long id, @Valid @RequestBody StudyYearDTO dto) {
        StudyYearResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<StudyYearResponse>builder()
                        .message("Study year successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('STUDY_YEAR_DELETE')")
    @Operation(summary = "Study year delete", description = "Only users with STUDY_YEAR_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Study year successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('STUDY_YEAR_EDIT')")
    @Operation(summary = "Active or Disable study year", description = "Only users with STUDY_YEAR_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledStudyYear(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Study year status successfully changed").build()
        );
    }
}
