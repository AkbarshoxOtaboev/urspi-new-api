package uz.urspi.newurspi.position.controller;

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
import uz.urspi.newurspi.position.dto.PositionDTO;
import uz.urspi.newurspi.position.response.PositionListResponseApi;
import uz.urspi.newurspi.position.response.PositionResponse;
import uz.urspi.newurspi.position.response.PositionResponseApi;
import uz.urspi.newurspi.position.service.PositionService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
@Tag(name = "Position (Lavozim) rest api management controller")
public class PositionController {
    private final PositionService service;

    @PostMapping
    @PreAuthorize("hasAuthority('POSITION_CREATE')")
    @Operation(summary = "Create position (lavozim)", description = "Only users with POSITION_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PositionResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<PositionResponse>> create(@Valid @RequestBody PositionDTO dto) {
        PositionResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<PositionResponse>builder()
                        .message("Position successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('POSITION_VIEW')")
    @Operation(summary = "Fetch all positions", description = "Only users with POSITION_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PositionListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<PositionResponse>>> fetchAllPositions() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<PositionResponse>>builder()
                        .message("All positions fetched success")
                        .data(service.fetchAllPositions())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('POSITION_VIEW')")
    @Operation(summary = "Fetch position by id", description = "Only users with POSITION_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PositionResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<PositionResponse>> findPositionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<PositionResponse>builder()
                        .message("Position found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('POSITION_EDIT')")
    @Operation(summary = "Update position", description = "Only users with POSITION_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PositionResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<PositionResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PositionDTO dto
    ) {
        PositionResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<PositionResponse>builder()
                        .message("Position successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('POSITION_DELETE')")
    @Operation(summary = "Delete position", description = "Only users with POSITION_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Position successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('POSITION_EDIT')")
    @Operation(summary = "Active or Disable position", description = "Only users with POSITION_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledPosition(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Position status successfully changed").build()
        );
    }
}
