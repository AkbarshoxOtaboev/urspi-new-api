package uz.urspi.newurspi.photogallery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Photo gallery create/update dto")
public class PhotoGalleryDTO {

    @NotEmpty(message = "Photo title (uz) cannot be empty")
    @Schema(description = "Photo title in Uzbek")
    private String titleUz;

    @Schema(description = "Photo title in Russian")
    private String titleRu;

    @Schema(description = "Photo title in English")
    private String titleEn;

    @Schema(description = "Photo description in Uzbek")
    private String descriptionUz;

    @Schema(description = "Photo description in Russian")
    private String descriptionRu;

    @Schema(description = "Photo description in English")
    private String descriptionEn;

    @Schema(description = "Gallery image file")
    private MultipartFile image;
}
