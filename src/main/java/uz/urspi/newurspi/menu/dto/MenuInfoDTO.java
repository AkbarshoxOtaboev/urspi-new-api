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
@Schema(description = "Menu info create/update dto")
public class MenuInfoDTO {
    @NotNull
    private Long subCategoryId;
    @NotEmpty
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String contentUz;
    private String contentRu;
    private String contentEn;
    private MultipartFile photo;
    private LocalDate publishedAt;
    private Integer sortOrder;
}
