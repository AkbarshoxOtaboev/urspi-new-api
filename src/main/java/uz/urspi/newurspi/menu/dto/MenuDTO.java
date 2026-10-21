package uz.urspi.newurspi.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Menu create/update dto")
public class MenuDTO {
    @NotEmpty
    @Schema(example = "Faoliyat")
    private String nameUz;
    private String nameRu;
    private String nameEn;
    @NotEmpty
    @Schema(example = "faoliyat")
    private String slug;
    @Schema(example = "0")
    private Integer sortOrder;
}
