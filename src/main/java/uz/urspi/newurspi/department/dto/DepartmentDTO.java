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

    @NotEmpty(message = "Department name (uz) cannot be empty")
    @Schema(description = "Department name in Uzbek", example = "Dasturiy injiniring")
    private String nameUz;

    @Schema(description = "Department name in Russian", example = "Программная инженерия")
    private String nameRu;

    @Schema(description = "Department name in English", example = "Software Engineering")
    private String nameEn;

    @Schema(description = "Department description in Uzbek")
    private String descriptionUz;

    @Schema(description = "Department description in Russian")
    private String descriptionRu;

    @Schema(description = "Department description in English")
    private String descriptionEn;

    @NotNull(message = "Faculty id cannot be null")
    @Schema(description = "Owning faculty id", example = "1")
    private Long facultyId;
}
