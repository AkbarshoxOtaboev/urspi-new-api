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
import uz.urspi.newurspi.menu.dto.MenuDTO;
import uz.urspi.newurspi.menu.response.MenuListResponseApi;
import uz.urspi.newurspi.menu.response.MenuResponse;
import uz.urspi.newurspi.menu.response.MenuResponseApi;
import uz.urspi.newurspi.menu.service.MenuService;
import uz.urspi.newurspi.utils.RestApiResponse;
import uz.urspi.newurspi.utils.VoidApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Tag(name = "Menu management")
public class MenuController {
    private final MenuService service;

    @PostMapping
    @PreAuthorize("hasAuthority('MENU_CREATE')")
    @Operation(summary = "Create menu")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MenuResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuResponse>> create(@Valid @RequestBody MenuDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<MenuResponse>builder().message("Menu created").data(service.create(dto)).build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuListResponseApi.class)))
    public ResponseEntity<RestApiResponse<List<MenuResponse>>> fetchAll() {
        return ResponseEntity.ok(RestApiResponse.<List<MenuResponse>>builder().message("Menus fetched").data(service.fetchAll()).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_VIEW')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(RestApiResponse.<MenuResponse>builder().message("Menu found").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MenuResponseApi.class)))
    public ResponseEntity<RestApiResponse<MenuResponse>> update(@PathVariable Long id, @Valid @RequestBody MenuDTO dto) {
        return ResponseEntity.ok(RestApiResponse.<MenuResponse>builder().message("Menu updated").data(service.update(id, dto)).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_DELETE')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Menu deleted").build());
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('MENU_EDIT')")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VoidApiResponse.class)))
    public ResponseEntity<RestApiResponse<Void>> changeStatus(@PathVariable Long id) {
        service.changeStatus(id);
        return ResponseEntity.ok(RestApiResponse.<Void>builder().message("Menu status changed").build());
    }
}
