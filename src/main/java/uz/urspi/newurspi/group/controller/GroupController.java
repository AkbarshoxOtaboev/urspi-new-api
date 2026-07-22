package uz.urspi.newurspi.group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import uz.urspi.newurspi.group.dto.GroupDTO;
import uz.urspi.newurspi.group.response.GroupResponse;
import uz.urspi.newurspi.group.service.GroupService;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Group rest api management controller")
public class GroupController {
    private final GroupService service;

    @PostMapping
    @PreAuthorize("hasAuthority('GROUP_CREATE')")
    @Operation(summary = "Group create", description = "Only users with GROUP_CREATE permission can use it.")
    @ApiResponse(responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GroupResponse.class)
            )
    )
    public ResponseEntity<?> create(@Valid @RequestBody GroupDTO dto) {
        GroupResponse response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestApiResponse.<GroupResponse>builder()
                        .message("Group successfully created")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GROUP_VIEW')")
    @Operation(summary = "Fetch all groups", description = "Only users with GROUP_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GroupResponse.class))
            ))
    public ResponseEntity<?> fetchAllGroups() {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<GroupResponse>>builder()
                        .message("All groups fetched success")
                        .data(service.fetchAllGroups())
                        .build()
        );
    }

    @GetMapping("/faculty/{facultyId}")
    @PreAuthorize("hasAuthority('GROUP_VIEW')")
    @Operation(summary = "Fetch groups by faculty id", description = "Only users with GROUP_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GroupResponse.class))
            ))
    public ResponseEntity<?> fetchByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<GroupResponse>>builder()
                        .message("Groups fetched success")
                        .data(service.fetchByFacultyId(facultyId))
                        .build()
        );
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAuthority('GROUP_VIEW')")
    @Operation(summary = "Fetch groups by department id", description = "Only users with GROUP_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GroupResponse.class))
            ))
    public ResponseEntity<?> fetchByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok().body(
                RestApiResponse.<List<GroupResponse>>builder()
                        .message("Groups fetched success")
                        .data(service.fetchByDepartmentId(departmentId))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_VIEW')")
    @Operation(summary = "Fetch group by id", description = "Only users with GROUP_VIEW permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GroupResponse.class)
            ))
    public ResponseEntity<?> findGroupById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                RestApiResponse.<GroupResponse>builder()
                        .message("Group found success")
                        .data(service.findById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_EDIT')")
    @Operation(summary = "Update group", description = "Only users with GROUP_EDIT permission can use it.")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GroupResponse.class)
            ))
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody GroupDTO dto) {
        GroupResponse response = service.update(id, dto);
        return ResponseEntity.ok(
                RestApiResponse.<GroupResponse>builder()
                        .message("Group successfully updated")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_DELETE')")
    @Operation(summary = "Group delete", description = "Only users with GROUP_DELETE permission can use it.")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Group successfully deleted").build()
        );
    }

    @PutMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('GROUP_EDIT')")
    @Operation(summary = "Active or Disable group", description = "Only users with GROUP_EDIT permission can use it.")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) {
        service.activeOrDisabledGroup(id);
        return ResponseEntity.ok().body(
                RestApiResponse.<Void>builder().message("Group status successfully changed").build()
        );
    }
}
