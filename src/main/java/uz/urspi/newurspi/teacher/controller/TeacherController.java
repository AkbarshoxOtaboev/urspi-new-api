package uz.urspi.newurspi.teacher.controller;

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
import uz.urspi.newurspi.teacher.dto.TeacherDTO;
import uz.urspi.newurspi.teacher.response.TeacherListResponseApi;
import uz.urspi.newurspi.teacher.response.TeacherResponse;
import uz.urspi.newurspi.teacher.response.TeacherResponseApi;
import uz.urspi.newurspi.teacher.service.TeacherService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher rest api management controller")
public class TeacherController {
    private final TeacherService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('TEACHER_CREATE')")
    @Operation(summary = "Create teacher", description = "Only users with TEACHER_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<TeacherResponse>> create(@Valid @ModelAttribute TeacherDTO dto) {
        TeacherResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<TeacherResponse>builder()
                        .message("Teacher successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('TEACHER_VIEW')")
    @Operation(summary = "Fetch all teachers", description = "Only users with TEACHER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<TeacherResponse>>> fetchAllTeachers() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<TeacherResponse>>builder()
                        .message("All teachers fetched success")
                        .data(service.fetchAllTeachers())
                        .build()
        );
    }

    @GetMapping("/faculty/{facultyId}")
    @PreAuthorize("hasAuthority('TEACHER_VIEW')")
    @Operation(summary = "Fetch teachers by faculty id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<TeacherResponse>>> fetchByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<TeacherResponse>>builder()
                        .message("Teachers fetched success")
                        .data(service.fetchByFacultyId(facultyId))
                        .build()
        );
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAuthority('TEACHER_VIEW')")
    @Operation(summary = "Fetch teachers by department (kafedra) id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<TeacherResponse>>> fetchByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<TeacherResponse>>builder()
                        .message("Teachers fetched success")
                        .data(service.fetchByDepartmentId(departmentId))
                        .build()
        );
    }

    @GetMapping("/position/{positionId}")
    @PreAuthorize("hasAuthority('TEACHER_VIEW')")
    @Operation(summary = "Fetch teachers by position (lavozim) id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<TeacherResponse>>> fetchByPositionId(@PathVariable Long positionId) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<TeacherResponse>>builder()
                        .message("Teachers fetched success")
                        .data(service.fetchByPositionId(positionId))
                        .build()
        );
    }

    @GetMapping("/academic-degree/{academicDegreeId}")
    @PreAuthorize("hasAuthority('TEACHER_VIEW')")
    @Operation(summary = "Fetch teachers by academic degree (ilmiy daraja) id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<TeacherResponse>>> fetchByAcademicDegreeId(
            @PathVariable Long academicDegreeId
    ) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<TeacherResponse>>builder()
                        .message("Teachers fetched success")
                        .data(service.fetchByAcademicDegreeId(academicDegreeId))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER_VIEW')")
    @Operation(summary = "Fetch teacher by id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<TeacherResponse>> findTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<TeacherResponse>builder()
                        .message("Teacher found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('TEACHER_EDIT')")
    @Operation(summary = "Update teacher", description = "Photo and CV are updated only if a new file is sent.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TeacherResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<TeacherResponse>> update(
            @PathVariable Long id,
            @Valid @ModelAttribute TeacherDTO dto
    ) {
        TeacherResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<TeacherResponse>builder()
                        .message("Teacher successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER_DELETE')")
    @Operation(summary = "Delete teacher")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Teacher successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('TEACHER_EDIT')")
    @Operation(summary = "Active or Disable teacher")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledTeacher(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Teacher status successfully changed").build()
        );
    }
}
