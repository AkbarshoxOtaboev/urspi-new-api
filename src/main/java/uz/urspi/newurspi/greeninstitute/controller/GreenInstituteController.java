package uz.urspi.newurspi.greeninstitute.controller;

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
import uz.urspi.newurspi.greeninstitute.dto.GreenInstituteDTO;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteListResponseApi;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteLocalizedListResponseApi;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteLocalizedResponse;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteResponse;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteResponseApi;
import uz.urspi.newurspi.greeninstitute.service.GreenInstituteService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/green-institutes")
@RequiredArgsConstructor
@Tag(name = "Green institute rest api management")
public class GreenInstituteController {
    private final GreenInstituteService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('GREEN_INSTITUTE_CREATE')")
    @Operation(summary = "Green institute create")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = GreenInstituteResponseApi.class)))
    public ResponseEntity<RestApiResponse<GreenInstituteResponse>> create(@Valid @ModelAttribute GreenInstituteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<GreenInstituteResponse>builder()
                        .message("Green institute successfully created")
                        .data(service.create(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GREEN_INSTITUTE_VIEW')")
    @Operation(summary = "Fetch all green institute items")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GreenInstituteListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<GreenInstituteResponse>>> fetchAll() {
        return ResponseEntity.ok(RestApiResponse.<List<GreenInstituteResponse>>builder()
                .message("All green institute items fetched success")
                .data(service.fetchAll())
                .build());
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('GREEN_INSTITUTE_VIEW')")
    @Operation(summary = "Fetch green institute items by language")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GreenInstituteLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<GreenInstituteLocalizedResponse>>> fetchAllByLang(@PathVariable Language lang) {
        return ResponseEntity.ok(RestApiResponse.<List<GreenInstituteLocalizedResponse>>builder()
                .message("All green institute items fetched success")
                .data(service.fetchAllByLang(lang))
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GREEN_INSTITUTE_VIEW')")
    @Operation(summary = "Fetch green institute by id")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GreenInstituteResponseApi.class)))
    public ResponseEntity<RestApiResponse<GreenInstituteResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<GreenInstituteResponse>builder()
                .message("Green institute found success")
                .data(service.findById(id))
                .build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('GREEN_INSTITUTE_EDIT')")
    @Operation(summary = "Update green institute")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GreenInstituteResponseApi.class)))
    public ResponseEntity<RestApiResponse<GreenInstituteResponse>> update(@PathVariable Long id, @Valid @ModelAttribute GreenInstituteDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<GreenInstituteResponse>builder()
                .message("Green institute successfully updated")
                .data(service.update(id, dto))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('GREEN_INSTITUTE_DELETE')")
    @Operation(summary = "Delete green institute")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Green institute successfully deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('GREEN_INSTITUTE_EDIT')")
    @Operation(summary = "Active or disable green institute")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabled(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Green institute status successfully changed").build());
    }
}
