package uz.urspi.newurspi.position.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Position (Lavozim) create/update dto")
public class PositionDTO {

    @NotEmpty(message = "Position name cannot be empty")
    @Schema(description = "Lavozim nomi", example = "Dotsent")
    private String name;

    @Schema(description = "Lavozim tavsifi", example = "Kafedra dotsenti")
    private String description;
}
