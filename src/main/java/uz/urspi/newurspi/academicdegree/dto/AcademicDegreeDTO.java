package uz.urspi.newurspi.academicdegree.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Academic degree (Ilmiy daraja) create/update dto")
public class AcademicDegreeDTO {

    @NotEmpty(message = "Academic degree name cannot be empty")
    @Schema(description = "Ilmiy daraja nomi", example = "PhD")
    private String name;

    @Schema(description = "Ilmiy daraja tavsifi", example = "Falsafa doktori")
    private String description;
}
