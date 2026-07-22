package uz.urspi.newurspi.faculty.controller;

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
import uz.urspi.newurspi.faculty.dto.FacultyDTO;
import uz.urspi.newurspi.faculty.response.FacultyListResponseApi;
import uz.urspi.newurspi.faculty.response.FacultyResponse;
import uz.urspi.newurspi.faculty.response.FacultyResponseApi;
import uz.urspi.newurspi.faculty.service.FacultyService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
@Tag(name = "Faculty rest api management controller")
public class FacultyController {
    private final FacultyService service;

    @PostMapping
    @PreAuthorize("hasAuthority('FACULTY_CREATE')")
    @Operation(summary = "Faculty create", description = "Only users with FACULTY_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FacultyResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<FacultyResponse>> create(@Valid @RequestBody FacultyDTO dto) {
        FacultyResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<FacultyResponse>builder()
                        .message("Faculty successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FACULTY_VIEW')")
    @Operation(summary = "Fetch all faculties", description = "Only users with FACULTY_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FacultyListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<FacultyResponse>>> fetchAllFaculties() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<FacultyResponse>>builder()
                        .message("All faculties fetched success")
                        .data(service.fetchAllFaculties())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('FACULTY_VIEW')")
    @Operation(summary = "Fetch faculty by id", description = "Only users with FACULTY_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FacultyResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<FacultyResponse>> findFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<FacultyResponse>builder()
                        .message("Faculty found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('FACULTY_EDIT')")
    @Operation(summary = "Update faculty", description = "Only users with FACULTY_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FacultyResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<FacultyResponse>> update(@PathVariable Long id, @Valid @RequestBody FacultyDTO dto) {
        FacultyResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<FacultyResponse>builder()
                        .message("Faculty successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('FACULTY_DELETE')")
    @Operation(summary = "Faculty delete", description = "Only users with FACULTY_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Faculty successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('FACULTY_EDIT')")
    @Operation(summary = "Active or Disable faculty", description = "Only users with FACULTY_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledFaculty(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Faculty status successfully changed").build()
        );
    }
}