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
import uz.urspi.newurspi.menu.dto.MenuCategoryDTO;
import uz.urspi.newurspi.menu.response.MenuCategoryListResponseApi;
import uz.urspi.newurspi.menu.response.MenuCategoryResponse;
import uz.urspi.newurspi.menu.response.MenuCategoryResponseApi;
import uz.urspi.newurspi.menu.service.MenuCategoryService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/menu-categories")
@RequiredArgsConstructor
@Tag(name = "Menu Category management")
public class MenuCategoryController {
    private final MenuCategoryService service;

    @PostMapping
    @PreAuthorize("hasAuthority('MENU_CATEGORY_CREATE')")
    @Operation(summary = "Create menu category")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MenuCategoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuCategoryResponse>> create(@Valid @RequestBody MenuCategoryDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<MenuCategoryResponse>builder().message("Category created").data(service.create(dto)).build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_CATEGORY_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuCategoryListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<MenuCategoryResponse>>> fetchAll(@RequestParam(required = false) Long menuId) {
        return ResponseEntity.ok(RestApiResponse.<List<MenuCategoryResponse>>builder()
                .message("Categories fetched").data(service.fetchAll(menuId)).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_CATEGORY_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuCategoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuCategoryResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<MenuCategoryResponse>builder().message("Category found").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_CATEGORY_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuCategoryResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuCategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody MenuCategoryDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<MenuCategoryResponse>builder().message("Category updated").data(service.update(id, dto)).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_CATEGORY_DELETE')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Category deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('MENU_CATEGORY_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.changeStatus(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Category status changed").build());
    }
}
