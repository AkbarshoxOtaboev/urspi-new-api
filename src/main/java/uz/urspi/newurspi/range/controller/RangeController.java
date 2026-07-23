package uz.urspi.newurspi.range.controller;

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
import uz.urspi.newurspi.range.dto.RangeDTO;
import uz.urspi.newurspi.range.response.RangeListResponseApi;
import uz.urspi.newurspi.range.response.RangeResponse;
import uz.urspi.newurspi.range.response.RangeResponseApi;
import uz.urspi.newurspi.range.service.RangeService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/ranges")
@RequiredArgsConstructor
@Tag(name = "Range rest api management controller")
public class RangeController {
    private final RangeService service;

    @PostMapping
    @PreAuthorize("hasAuthority('RANGE_CREATE')")
    @Operation(summary = "Range create", description = "Only users with RANGE_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RangeResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<RangeResponse>> create(@Valid @RequestBody RangeDTO dto) {
        RangeResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<RangeResponse>builder()
                        .message("Range successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('RANGE_VIEW')")
    @Operation(summary = "Fetch all ranges", description = "Only users with RANGE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RangeListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<RangeResponse>>> fetchAllRanges() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<RangeResponse>>builder()
                        .message("All ranges fetched success")
                        .data(service.fetchAllRanges())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('RANGE_VIEW')")
    @Operation(summary = "Fetch range by id", description = "Only users with RANGE_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RangeResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<RangeResponse>> findRangeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<RangeResponse>builder()
                        .message("Range found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('RANGE_EDIT')")
    @Operation(summary = "Update range", description = "Only users with RANGE_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RangeResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<RangeResponse>> update(@PathVariable Long id, @Valid @RequestBody RangeDTO dto) {
        RangeResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<RangeResponse>builder()
                        .message("Range successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('RANGE_DELETE')")
    @Operation(summary = "Range delete", description = "Only users with RANGE_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Range successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('RANGE_EDIT')")
    @Operation(summary = "Active or Disable range", description = "Only users with RANGE_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledRange(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Range status successfully changed").build()
        );
    }
}
