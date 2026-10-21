package uz.urspi.newurspi.hemisconfig.controller;

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
import uz.urspi.newurspi.hemisconfig.dto.HemisConfigDTO;
import uz.urspi.newurspi.hemisconfig.response.HemisConfigListResponseApi;
import uz.urspi.newurspi.hemisconfig.response.HemisConfigResponse;
import uz.urspi.newurspi.hemisconfig.response.HemisConfigResponseApi;
import uz.urspi.newurspi.hemisconfig.service.HemisConfigService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/hemis-configs")
@RequiredArgsConstructor
@Tag(name = "HEMIS Config rest api management controller")
public class HemisConfigController {

    private final HemisConfigService service;

    @PostMapping
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_CREATE')")
    @Operation(summary = "Create HEMIS config")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HemisConfigResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<HemisConfigResponse>> create(@Valid @RequestBody HemisConfigDTO dto) {
        HemisConfigResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<HemisConfigResponse>builder()
                        .message("Hemis config successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_VIEW')")
    @Operation(summary = "Fetch all HEMIS configs")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HemisConfigListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<HemisConfigResponse>>> fetchAll() {
        return ResponseEntity.ok(
                RestApiResponse.<List<HemisConfigResponse>>builder()
                        .message("All hemis configs fetched success")
                        .data(service.fetchAll())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_VIEW')")
    @Operation(summary = "Fetch HEMIS config by id")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HemisConfigResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<HemisConfigResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                RestApiResponse.<HemisConfigResponse>builder()
                        .message("Hemis config found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_EDIT')")
    @Operation(summary = "Update HEMIS config")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HemisConfigResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<HemisConfigResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody HemisConfigDTO dto
    ) {
        return ResponseEntity.ok(
                RestApiResponse.<HemisConfigResponse>builder()
                        .message("Hemis config successfully updated")
                        .data(service.update(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_DELETE')")
    @Operation(summary = "Delete HEMIS config")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                RestApiResponse.<Void>builder().message("Hemis config successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_EDIT')")
    @Operation(summary = "Active or Disable HEMIS config")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabled(id);
        return ResponseEntity.ok(
                RestApiResponse.<Void>builder().message("Hemis config status successfully changed").build()
        );
    }
}
