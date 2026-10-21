package uz.urspi.newurspi.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Menu material create/update dto")
public class MenuMaterialDTO {
    @NotNull
    private Long infoId;
    @Schema(example = "2024")
    private Integer year;
    @NotEmpty
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private MultipartFile file;
    private MultipartFile cover;
    private LocalDate publishedAt;
}
