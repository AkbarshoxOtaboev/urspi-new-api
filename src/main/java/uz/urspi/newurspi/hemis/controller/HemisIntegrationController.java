package uz.urspi.newurspi.hemis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.urspi.newurspi.hemis.response.HemisConnectionTestResponse;
import uz.urspi.newurspi.hemis.response.HemisConnectionTestResponseApi;
import uz.urspi.newurspi.hemis.response.HemisSyncResultResponse;
import uz.urspi.newurspi.hemis.response.HemisSyncResultResponseApi;
import uz.urspi.newurspi.hemis.service.HemisIntegrationService;
import uz.urspi.newurspi.hemis.sync.HemisSyncResource;
import uz.urspi.newurspi.utils.RestApiResponse;

@RestController
@RequestMapping("/api/hemis")
@RequiredArgsConstructor
@Tag(name = "HEMIS Integration controller")
public class HemisIntegrationController {

    private final HemisIntegrationService service;

    @GetMapping("/connection-test")
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_VIEW')")
    @Operation(summary = "Test active HEMIS config connection")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HemisConnectionTestResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<HemisConnectionTestResponse>> connectionTest() {
        return ResponseEntity.ok(
                RestApiResponse.<HemisConnectionTestResponse>builder()
                        .message("HEMIS connection test finished")
                        .data(service.testConnection())
                        .build()
        );
    }

    @PostMapping("/sync/{resource}")
    @PreAuthorize("hasAuthority('HEMIS_CONFIG_EDIT')")
    @Operation(summary = "Sync HEMIS resource into local cache tables",
            description = "resource: DEPARTMENT or EMPLOYEE")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HemisSyncResultResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<HemisSyncResultResponse>> sync(
            @PathVariable HemisSyncResource resource
    ) {
        HemisSyncResultResponse result = service.sync(resource);
        return ResponseEntity.ok(
                RestApiResponse.<HemisSyncResultResponse>builder()
                        .message(result.isSuccess() ? "HEMIS sync completed" : "HEMIS sync failed")
                        .data(result)
                        .build()
        );
    }
}
