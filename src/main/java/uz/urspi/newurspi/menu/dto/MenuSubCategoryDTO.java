package uz.urspi.newurspi.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.urspi.newurspi.menu.MenuContentType;

@Getter
@Setter
@Schema(description = "Menu subcategory create/update dto")
public class MenuSubCategoryDTO {
    @NotNull
    private Long categoryId;
    @NotEmpty
    @Schema(example = "UrDPI ilmiy jurnallari")
    private String nameUz;
    private String nameRu;
    private String nameEn;
    @NotEmpty
    @Schema(example = "urdpi-ilmiy-jurnallari")
    private String slug;
    @NotNull
    private MenuContentType contentType;
    @Schema(description = "TABLE ustunlari JSON yoki vergul bilan", example = "[\"№\",\"Sahifa nomi\",\"Ko'rish\"]")
    private String tableColumns;
    @Schema(description = "LINK uchun path", example = "/documents")
    private String linkPath;
    @Schema(example = "DOCUMENT")
    private String linkTargetType;
    private Integer sortOrder;
}
