package uz.urspi.newurspi.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Center create/update dto")
public class CenterDTO {

    @NotEmpty(message = "Center name (uz) cannot be empty")
    @Schema(description = "Center name in Uzbek", example = "Axborot texnologiyalari markazi")
    private String nameUz;

    @Schema(description = "Center name in Russian", example = "Центр информационных технологий")
    private String nameRu;

    @Schema(description = "Center name in English", example = "Information Technology Center")
    private String nameEn;

    @Schema(description = "Center description in Uzbek")
    private String descriptionUz;

    @Schema(description = "Center description in Russian")
    private String descriptionRu;

    @Schema(description = "Center description in English")
    private String descriptionEn;
}
