package uz.urspi.newurspi.employee.controller;

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
import uz.urspi.newurspi.employee.dto.EmployeeDTO;
import uz.urspi.newurspi.employee.response.*;
import uz.urspi.newurspi.employee.service.EmployeeService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee rest api management controller")
public class EmployeeController {
    private final EmployeeService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE')")
    @Operation(summary = "Employee create", description = "Only users with EMPLOYEE_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponseApi.class)))
    public ResponseEntity<RestApiResponse<EmployeeResponse>> create(@Valid @ModelAttribute EmployeeDTO dto) {
        EmployeeResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<EmployeeResponse>builder()
                        .message("Employee successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    @Operation(summary = "Fetch all employees", description = "Only users with EMPLOYEE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<EmployeeResponse>>> fetchAllEmployees() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<EmployeeResponse>>builder()
                        .message("All employees fetched success")
                        .data(service.fetchAllEmployees())
                        .build()
        );
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    @Operation(summary = "Fetch all employees by language", description = "Only users with EMPLOYEE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<EmployeeLocalizedResponse>>> fetchAllEmployeesByLang(@PathVariable Language lang) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<EmployeeLocalizedResponse>>builder()
                        .message("All employees fetched success")
                        .data(service.fetchAllEmployeesByLang(lang))
                        .build()
        );
    }

    @GetMapping("/center/{centerId}/lang/{lang}")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    @Operation(summary = "Fetch employees by center and language", description = "Only users with EMPLOYEE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<EmployeeLocalizedResponse>>> fetchByCenterIdByLang(
            @PathVariable Long centerId, @PathVariable Language lang) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<EmployeeLocalizedResponse>>builder()
                        .message("Employees by center fetched success")
                        .data(service.fetchByCenterIdByLang(centerId, lang))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    @Operation(summary = "Fetch employee by id", description = "Only users with EMPLOYEE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponseApi.class)))
    public ResponseEntity<RestApiResponse<EmployeeResponse>> findEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<EmployeeResponse>builder()
                        .message("Employee found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('EMPLOYEE_EDIT')")
    @Operation(summary = "Update employee", description = "Only users with EMPLOYEE_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponseApi.class)))
    public ResponseEntity<RestApiResponse<EmployeeResponse>> update(@PathVariable Long id, @Valid @ModelAttribute EmployeeDTO dto) {
        EmployeeResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<EmployeeResponse>builder()
                        .message("Employee successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_DELETE')")
    @Operation(summary = "Employee delete", description = "Only users with EMPLOYEE_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Employee successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_EDIT')")
    @Operation(summary = "Active or Disable employee", description = "Only users with EMPLOYEE_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledEmployee(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Employee status successfully changed").build()
        );
    }
}
