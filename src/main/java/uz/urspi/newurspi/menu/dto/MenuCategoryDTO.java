package uz.urspi.newurspi.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Menu category create/update dto")
public class MenuCategoryDTO {
    @NotNull
    private Long menuId;
    @NotEmpty
    @Schema(example = "Ilmiy innovatsion faoliyat")
    private String nameUz;
    private String nameRu;
    private String nameEn;
    @NotEmpty
    @Schema(example = "ilmiy-innovatsion-faoliyat")
    private String slug;
    private Integer sortOrder;
}
