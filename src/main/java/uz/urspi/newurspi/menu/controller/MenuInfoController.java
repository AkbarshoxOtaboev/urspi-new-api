package uz.urspi.newurspi.menu.controller;

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
import uz.urspi.newurspi.menu.dto.MenuInfoDTO;
import uz.urspi.newurspi.menu.response.MenuInfoListResponseApi;
import uz.urspi.newurspi.menu.response.MenuInfoResponse;
import uz.urspi.newurspi.menu.response.MenuInfoResponseApi;
import uz.urspi.newurspi.menu.service.MenuInfoService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/menu-infos")
@RequiredArgsConstructor
@Tag(name = "Menu Info management")
public class MenuInfoController {
    private final MenuInfoService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MENU_INFO_CREATE')")
    @Operation(summary = "Create menu info (multipart)")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MenuInfoResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuInfoResponse>> create(@Valid @ModelAttribute MenuInfoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<MenuInfoResponse>builder().message("Info created").data(service.create(dto)).build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_INFO_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuInfoListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<MenuInfoResponse>>> fetchAll(@RequestParam(required = false) Long subCategoryId) {
        return ResponseEntity.ok(RestApiResponse.<List<MenuInfoResponse>>builder()
                .message("Infos fetched").data(service.fetchAll(subCategoryId)).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_INFO_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuInfoResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuInfoResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<MenuInfoResponse>builder().message("Info found").data(service.findById(id)).build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MENU_INFO_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuInfoResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuInfoResponse>> update(@PathVariable Long id, @Valid @ModelAttribute MenuInfoDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<MenuInfoResponse>builder().message("Info updated").data(service.update(id, dto)).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_INFO_DELETE')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Info deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('MENU_INFO_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.changeStatus(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Info status changed").build());
    }
}
