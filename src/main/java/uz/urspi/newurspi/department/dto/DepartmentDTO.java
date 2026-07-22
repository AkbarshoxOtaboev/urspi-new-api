package uz.urspi.newurspi.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Department create/update dto")
public class DepartmentDTO {

    @NotEmpty(message = "Department name cannot be empty")
    @Schema(description = "Department name", example = "Software Engineering")
    private String name;

    @Schema(description = "Department description", example = "Department responsible for SE programs")
    private String description;

    @NotNull(message = "Faculty id cannot be null")
    @Schema(description = "Owning faculty id", example = "1")
    private Long facultyId;
}
