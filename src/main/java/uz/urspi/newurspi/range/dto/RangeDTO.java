package uz.urspi.newurspi.range.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Range create/update dto")
public class RangeDTO {

    @NotEmpty(message = "Range name cannot be empty")
    @Schema(description = "Range name", example = "1-kurs")
    private String name;
}
