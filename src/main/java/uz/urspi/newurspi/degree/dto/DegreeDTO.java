package uz.urspi.newurspi.degree.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Degree create/update dto")
public class DegreeDTO {

    @NotEmpty(message = "Degree name cannot be empty")
    @Schema(description = "Degree name", example = "Bachelor")
    private String name;
}
