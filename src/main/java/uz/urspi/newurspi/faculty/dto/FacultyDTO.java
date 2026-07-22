package uz.urspi.newurspi.faculty.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Faculty create/update dto")
public class FacultyDTO {

    @NotEmpty(message = "Faculty code cannot be empty")
    @Schema(description = "Unique faculty code", example = "FIT")
    private String code;

    @NotEmpty(message = "Faculty name cannot be empty")
    @Schema(description = "Faculty name", example = "Faculty of Information Technologies")
    private String name;

    @Schema(description = "Faculty description", example = "Faculty responsible for IT programs")
    private String description;
}
