package uz.urspi.newurspi.dormitory.controller;

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
import uz.urspi.newurspi.dormitory.dto.DormitoryDTO;
import uz.urspi.newurspi.dormitory.response.DormitoryListResponseApi;
import uz.urspi.newurspi.dormitory.response.DormitoryLocalizedListResponseApi;
import uz.urspi.newurspi.dormitory.response.DormitoryLocalizedResponse;
import uz.urspi.newurspi.dormitory.response.DormitoryResponse;
import uz.urspi.newurspi.dormitory.response.DormitoryResponseApi;
import uz.urspi.newurspi.dormitory.service.DormitoryService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/dormitories")
@RequiredArgsConstructor
@Tag(name = "Dormitory rest api management")
public class DormitoryController {
    private final DormitoryService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('DORMITORY_CREATE')")
    @Operation(summary = "Dormitory create")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = DormitoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<DormitoryResponse>> create(@Valid @ModelAttribute DormitoryDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<DormitoryResponse>builder()
                        .message("Dormitory successfully created")
                        .data(service.create(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DORMITORY_VIEW')")
    @Operation(summary = "Fetch all dormitories")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DormitoryListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<DormitoryResponse>>> fetchAll() {
        return ResponseEntity.ok(RestApiResponse.<List<DormitoryResponse>>builder()
                .message("All dormitories fetched success")
                .data(service.fetchAll())
                .build());
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('DORMITORY_VIEW')")
    @Operation(summary = "Fetch dormitories by language")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DormitoryLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<DormitoryLocalizedResponse>>> fetchAllByLang(@PathVariable Language lang) {
        return ResponseEntity.ok(RestApiResponse.<List<DormitoryLocalizedResponse>>builder()
                .message("All dormitories fetched success")
                .data(service.fetchAllByLang(lang))
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DORMITORY_VIEW')")
    @Operation(summary = "Fetch dormitory by id")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DormitoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<DormitoryResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<DormitoryResponse>builder()
                .message("Dormitory found success")
                .data(service.findById(id))
                .build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('DORMITORY_EDIT')")
    @Operation(summary = "Update dormitory")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DormitoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<DormitoryResponse>> update(@PathVariable Long id, @Valid @ModelAttribute DormitoryDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<DormitoryResponse>builder()
                .message("Dormitory successfully updated")
                .data(service.update(id, dto))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DORMITORY_DELETE')")
    @Operation(summary = "Delete dormitory")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Dormitory successfully deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('DORMITORY_EDIT')")
    @Operation(summary = "Active or disable dormitory")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabled(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Dormitory status successfully changed").build());
    }
}
