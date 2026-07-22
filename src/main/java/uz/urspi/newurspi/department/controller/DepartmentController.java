package uz.urspi.newurspi.department.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import uz.urspi.newurspi.department.dto.DepartmentDTO;
import uz.urspi.newurspi.department.response.DepartmentResponse;
import uz.urspi.newurspi.department.service.DepartmentService;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department rest api management controller")
public class DepartmentController {
    private final DepartmentService service;

    @PostMapping
    @PreAuthorize("hasAuthority('DEPARTMENT_CREATE')")
    @Operation(summary = "Department create", description = "Only users with DEPARTMENT_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentResponse.class)
            )
    )
    public ResponseEntity<?> create(@Valid @RequestBody DepartmentDTO dto) {
        DepartmentResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<DepartmentResponse>builder()
                        .message("Department successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DEPARTMENT_VIEW')")
    @Operation(summary = "Fetch all departments", description = "Only users with DEPARTMENT_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DepartmentResponse.class))
            ))
    public ResponseEntity<?> fetchAllDepartments() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<DepartmentResponse>>builder()
                        .message("All departments fetched success")
                        .data(service.fetchAllDepartments())
                        .build()
        );
    }

    @GetMapping("/faculty/{facultyId}")
    @PreAuthorize("hasAuthority('DEPARTMENT_VIEW')")
    @Operation(summary = "Fetch departments by faculty id", description = "Only users with DEPARTMENT_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DepartmentResponse.class))
            ))
    public ResponseEntity<?> fetchByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<DepartmentResponse>>builder()
                        .message("Departments fetched success")
                        .data(service.fetchByFacultyId(facultyId))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENT_VIEW')")
    @Operation(summary = "Fetch department by id", description = "Only users with DEPARTMENT_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentResponse.class)
            ))
    public ResponseEntity<?> findDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<DepartmentResponse>builder()
                        .message("Department found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENT_EDIT')")
    @Operation(summary = "Update department", description = "Only users with DEPARTMENT_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentResponse.class)
            ))
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DepartmentDTO dto) {
        DepartmentResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<DepartmentResponse>builder()
                        .message("Department successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENT_DELETE')")
    @Operation(summary = "Department delete", description = "Only users with DEPARTMENT_DELETE permission can use it.")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Department successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENT_EDIT')")
    @Operation(summary = "Active or Disable department", description = "Only users with DEPARTMENT_EDIT permission can use it.")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledDepartment(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Department status successfully changed").build()
        );
    }
}
