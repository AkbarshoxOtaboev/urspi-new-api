package uz.urspi.newurspi.permissions.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.urspi.newurspi.permissions.PermissionsResponse;
import uz.urspi.newurspi.permissions.response.PermissionsListResponseApi;
import uz.urspi.newurspi.permissions.service.PermissionsService;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions rest api management controller")
public class PermissionsController {
    private final PermissionsService service;

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_VIEW')")
    @Operation(summary = "Fetch all permissions")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PermissionsListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<PermissionsResponse>>> fetchAllPermissions() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<PermissionsResponse>>builder()
                        .message("All permissions fetched success")
                        .data(service.fetchAllPermissions())
                        .build()
        );
    }
}
