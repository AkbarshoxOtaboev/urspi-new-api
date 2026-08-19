package uz.urspi.newurspi.faculty.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Faculty create/update dto")
public class FacultyDTO {

    @NotEmpty(message = "Faculty code cannot be empty")
    @Schema(description = "Unique faculty code", example = "FIT")
    private String code;

    @NotEmpty(message = "Faculty name (uz) cannot be empty")
    @Schema(description = "Faculty name in Uzbek", example = "Axborot texnologiyalari fakulteti")
    private String nameUz;

    @Schema(description = "Faculty name in Russian", example = "Факультет информационных технологий")
    private String nameRu;

    @Schema(description = "Faculty name in English", example = "Faculty of Information Technologies")
    private String nameEn;

    @Schema(description = "Faculty description in Uzbek")
    private String descriptionUz;

    @Schema(description = "Faculty description in Russian")
    private String descriptionRu;

    @Schema(description = "Faculty description in English")
    private String descriptionEn;

    @Schema(description = "Faculty logo file")
    private MultipartFile logo;
}
