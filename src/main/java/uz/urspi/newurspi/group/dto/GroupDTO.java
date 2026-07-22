package uz.urspi.newurspi.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Group create/update dto")
public class GroupDTO {

    @NotEmpty(message = "Group name cannot be empty")
    @Schema(description = "Group name", example = "SE-21-01")
    private String name;

    @Schema(description = "Group description", example = "Software Engineering group, admitted 2021")
    private String description;

    @NotNull(message = "Faculty id cannot be null")
    @Schema(description = "Owning faculty id", example = "1")
    private Long facultyId;

    @Schema(description = "Owning department id (optional)", example = "1")
    private Long departmentId;
}
