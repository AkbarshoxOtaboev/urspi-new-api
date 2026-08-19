package uz.urspi.newurspi.leader.controller;

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
import uz.urspi.newurspi.leader.dto.LeaderDTO;
import uz.urspi.newurspi.leader.response.*;
import uz.urspi.newurspi.leader.service.LeaderService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/leaders")
@RequiredArgsConstructor
@Tag(name = "Leader rest api management controller")
public class LeaderController {
    private final LeaderService service;

    @PostMapping
    @PreAuthorize("hasAuthority('LEADER_CREATE')")
    @Operation(summary = "Leader create", description = "Only users with LEADER_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeaderResponseApi.class)
            )
    )
    public ResponseEntity<RestApiResponse<LeaderResponse>> create(@Valid @ModelAttribute LeaderDTO dto) {
        LeaderResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<LeaderResponse>builder()
                        .message("Leader successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('LEADER_VIEW')")
    @Operation(summary = "Fetch all leaders", description = "Only users with LEADER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeaderListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<LeaderResponse>>> fetchAllLeaders() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<LeaderResponse>>builder()
                        .message("All leaders fetched success")
                        .data(service.fetchAllLeaders())
                        .build()
        );
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('LEADER_VIEW')")
    @Operation(summary = "Fetch all leaders by language", description = "Only users with LEADER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeaderLocalizedListResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<List<LeaderLocalizedResponse>>> fetchAllLeadersByLang(@PathVariable Language lang) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<LeaderLocalizedResponse>>builder()
                        .message("All leaders fetched success")
                        .data(service.fetchAllLeadersByLang(lang))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('LEADER_VIEW')")
    @Operation(summary = "Fetch leader by id", description = "Only users with LEADER_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeaderResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<LeaderResponse>> findLeaderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<LeaderResponse>builder()
                        .message("Leader found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('LEADER_EDIT')")
    @Operation(summary = "Update leader", description = "Only users with LEADER_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LeaderResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<LeaderResponse>> update(@PathVariable Long id, @Valid @ModelAttribute LeaderDTO dto) {
        LeaderResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<LeaderResponse>builder()
                        .message("Leader successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('LEADER_DELETE')")
    @Operation(summary = "Leader delete", description = "Only users with LEADER_DELETE permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Leader successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('LEADER_EDIT')")
    @Operation(summary = "Active or Disable leader", description = "Only users with LEADER_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VoidApiResponse.class)
            ))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledLeader(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Leader status successfully changed").build()
        );
    }
}
