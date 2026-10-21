package uz.urspi.newurspi.menu.controller;

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
import uz.urspi.newurspi.menu.dto.MenuSubCategoryDTO;
import uz.urspi.newurspi.menu.response.MenuSubCategoryListResponseApi;
import uz.urspi.newurspi.menu.response.MenuSubCategoryResponse;
import uz.urspi.newurspi.menu.response.MenuSubCategoryResponseApi;
import uz.urspi.newurspi.menu.service.MenuSubCategoryService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/menu-subcategories")
@RequiredArgsConstructor
@Tag(name = "Menu SubCategory management")
public class MenuSubCategoryController {
    private final MenuSubCategoryService service;

    @PostMapping
    @PreAuthorize("hasAuthority('MENU_SUBCATEGORY_CREATE')")
    @Operation(summary = "Create subcategory (FEED/TABLE/LINK)")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MenuSubCategoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuSubCategoryResponse>> create(@Valid @RequestBody MenuSubCategoryDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<MenuSubCategoryResponse>builder().message("SubCategory created").data(service.create(dto)).build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_SUBCATEGORY_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuSubCategoryListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<MenuSubCategoryResponse>>> fetchAll(@RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(RestApiResponse.<List<MenuSubCategoryResponse>>builder()
                .message("SubCategories fetched").data(service.fetchAll(categoryId)).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_SUBCATEGORY_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuSubCategoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuSubCategoryResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<MenuSubCategoryResponse>builder().message("SubCategory found").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_SUBCATEGORY_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuSubCategoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuSubCategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody MenuSubCategoryDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<MenuSubCategoryResponse>builder().message("SubCategory updated").data(service.update(id, dto)).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_SUBCATEGORY_DELETE')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("SubCategory deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('MENU_SUBCATEGORY_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.changeStatus(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("SubCategory status changed").build());
    }
}
