package uz.urspi.newurspi.rental.controller;

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
import uz.urspi.newurspi.rental.dto.RentalDTO;
import uz.urspi.newurspi.rental.response.RentalListResponseApi;
import uz.urspi.newurspi.rental.response.RentalLocalizedListResponseApi;
import uz.urspi.newurspi.rental.response.RentalLocalizedResponse;
import uz.urspi.newurspi.rental.response.RentalResponse;
import uz.urspi.newurspi.rental.response.RentalResponseApi;
import uz.urspi.newurspi.rental.service.RentalService;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rental rest api management")
public class RentalController {
    private final RentalService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('RENTAL_CREATE')")
    @Operation(summary = "Rental create")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RentalResponseApi.class)))
    public ResponseEntity<RestApiResponse<RentalResponse>> create(@Valid @ModelAttribute RentalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<RentalResponse>builder()
                        .message("Rental successfully created")
                        .data(service.create(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('RENTAL_VIEW')")
    @Operation(summary = "Fetch all rentals")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RentalListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<RentalResponse>>> fetchAll() {
        return ResponseEntity.ok(RestApiResponse.<List<RentalResponse>>builder()
                .message("All rentals fetched success")
                .data(service.fetchAll())
                .build());
    }

    @GetMapping("/lang/{lang}")
    @PreAuthorize("hasAuthority('RENTAL_VIEW')")
    @Operation(summary = "Fetch rentals by language")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RentalLocalizedListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<RentalLocalizedResponse>>> fetchAllByLang(@PathVariable Language lang) {
        return ResponseEntity.ok(RestApiResponse.<List<RentalLocalizedResponse>>builder()
                .message("All rentals fetched success")
                .data(service.fetchAllByLang(lang))
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('RENTAL_VIEW')")
    @Operation(summary = "Fetch rental by id")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RentalResponseApi.class)))
    public ResponseEntity<RestApiResponse<RentalResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<RentalResponse>builder()
                .message("Rental found success")
                .data(service.findById(id))
                .build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('RENTAL_EDIT')")
    @Operation(summary = "Update rental")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RentalResponseApi.class)))
    public ResponseEntity<RestApiResponse<RentalResponse>> update(@PathVariable Long id, @Valid @ModelAttribute RentalDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<RentalResponse>builder()
                .message("Rental successfully updated")
                .data(service.update(id, dto))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('RENTAL_DELETE')")
    @Operation(summary = "Delete rental")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Rental successfully deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('RENTAL_EDIT')")
    @Operation(summary = "Active or disable rental")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.activeOrDisabled(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Rental status successfully changed").build());
    }
}
