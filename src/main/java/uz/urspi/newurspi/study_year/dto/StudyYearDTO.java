package uz.urspi.newurspi.study_year.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Study year create/update dto")
public class StudyYearDTO {

    @NotEmpty(message = "Study year cannot be empty")
    @Schema(description = "Study year", example = "2025-2026")
    private String year;
}
