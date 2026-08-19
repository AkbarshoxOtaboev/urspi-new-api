package uz.urspi.newurspi.permissions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.Action;
import uz.urspi.newurspi.utils.Resource;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Permission response object")
public class PermissionsResponse {
    @Schema(description = "Unique permission ID", example = "1")
    private Long id;

    @Schema(description = "Permission name", example = "USER_CREATE")
    private String name;

    @Schema(description = "Resource", example = "USER")
    private Resource resource;

    @Schema(description = "Action", example = "CREATE")
    private Action action;

    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
