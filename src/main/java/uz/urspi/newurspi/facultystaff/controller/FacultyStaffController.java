package uz.urspi.newurspi.facultystaff.controller;

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
import uz.urspi.newurspi.facultystaff.dto.FacultyStaffDTO;
import uz.urspi.newurspi.facultystaff.response.*;
import uz.urspi.newurspi.facultystaff.service.FacultyStaffService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/faculty-staff")
@RequiredArgsConstructor
@Tag(name = "Faculty staff (Dekan, zam-dekan, ...)")
public class FacultyStaffController {
    private final FacultyStaffService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('FACULTY_STAFF_CREATE')")
    @Operation(summary = "Create faculty staff")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = FacultyStaffResponseApi.class)))
    public ResponseEntity<RestApiResponse<FacultyStaffResponse>> create(@Valid @ModelAttribute FacultyStaffDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<FacultyStaffResponse>builder()
                        .message("Faculty staff successfully created")
                        .data(service.create(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FACULTY_STAFF_VIEW')")
    @Operation(summary = "Fetch all faculty staff")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FacultyStaffListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<FacultyStaffResponse>>> fetchAll() {
        return ResponseEntity.ok(RestApiResponse.<List<FacultyStaffResponse>>builder()
                .message("All faculty staff fetched success")
                .data(service.fetchAll())
                .build());
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('FACULTY_STAFF_VIEW')")
    @Operation(summary = "Fetch faculty staff by language")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FacultyStaffLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<FacultyStaffLocalizedResponse>>> fetchAllByLang(@PathVariable Language lang) {
        return ResponseEntity.ok(RestApiResponse.<List<FacultyStaffLocalizedResponse>>builder()
                .message("All faculty staff fetched success")
                .data(service.fetchAllByLang(lang))
                .build());
    }

    @GetMapping("/faculty/{facultyId}/lang/{lang}")
    @PreAuthorize("hasAuthority('FACULTY_STAFF_VIEW')")
    @Operation(summary = "Fetch faculty staff by faculty and language")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FacultyStaffLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<FacultyStaffLocalizedResponse>>> fetchByFaculty(
            @PathVariable Long facultyId, @PathVariable Language lang) {
        return ResponseEntity.ok(RestApiResponse.<List<FacultyStaffLocalizedResponse>>builder()
                .message("Faculty staff by faculty fetched success")
                .data(service.fetchByFacultyIdByLang(facultyId, lang))
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('FACULTY_STAFF_VIEW')")
    @Operation(summary = "Fetch faculty staff by id")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FacultyStaffResponseApi.class)))
    public ResponseEntity<RestApiResponse<FacultyStaffResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<FacultyStaffResponse>builder()
                .message("Faculty staff found success")
                .data(service.findById(id))
                .build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('FACULTY_STAFF_EDIT')")
    @Operation(summary = "Update faculty staff")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FacultyStaffResponseApi.class)))
    public ResponseEntity<RestApiResponse<FacultyStaffResponse>> update(
            @PathVariable Long id, @Valid @ModelAttribute FacultyStaffDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<FacultyStaffResponse>builder()
                .message("Faculty staff successfully updated")
                .data(service.update(id, dto))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('FACULTY_STAFF_DELETE')")
    @Operation(summary = "Delete faculty staff")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Faculty staff successfully deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('FACULTY_STAFF_EDIT')")
    @Operation(summary = "Active or disable faculty staff")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabled(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Faculty staff status successfully changed").build());
    }
}
