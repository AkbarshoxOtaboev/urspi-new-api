package uz.urspi.newurspi.semester.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Semester create/update dto")
public class SemesterDTO {

    @NotEmpty(message = "Semester name cannot be empty")
    @Schema(description = "Semester name", example = "1-semestr")
    private String name;
}
