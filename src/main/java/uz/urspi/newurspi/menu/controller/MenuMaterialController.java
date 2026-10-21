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
import uz.urspi.newurspi.menu.dto.MenuMaterialDTO;
import uz.urspi.newurspi.menu.response.MenuMaterialListResponseApi;
import uz.urspi.newurspi.menu.response.MenuMaterialResponse;
import uz.urspi.newurspi.menu.response.MenuMaterialResponseApi;
import uz.urspi.newurspi.menu.service.MenuMaterialService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/menu-materials")
@RequiredArgsConstructor
@Tag(name = "Menu Material management")
public class MenuMaterialController {
    private final MenuMaterialService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MENU_MATERIAL_CREATE')")
    @Operation(summary = "Create material (year + file)")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MenuMaterialResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuMaterialResponse>> create(@Valid @ModelAttribute MenuMaterialDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<MenuMaterialResponse>builder().message("Material created").data(service.create(dto)).build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_MATERIAL_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuMaterialListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<MenuMaterialResponse>>> fetchAll(
            @RequestParam(required = false) Long infoId,
            @RequestParam(required = false) Integer year
    ) {
        return ResponseEntity.ok(RestApiResponse.<List<MenuMaterialResponse>>builder()
                .message("Materials fetched").data(service.fetchAll(infoId, year)).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_MATERIAL_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuMaterialResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuMaterialResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<MenuMaterialResponse>builder().message("Material found").data(service.findById(id)).build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MENU_MATERIAL_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuMaterialResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuMaterialResponse>> update(@PathVariable Long id, @Valid @ModelAttribute MenuMaterialDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<MenuMaterialResponse>builder().message("Material updated").data(service.update(id, dto)).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_MATERIAL_DELETE')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Material deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('MENU_MATERIAL_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.changeStatus(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Material status changed").build());
    }
}
