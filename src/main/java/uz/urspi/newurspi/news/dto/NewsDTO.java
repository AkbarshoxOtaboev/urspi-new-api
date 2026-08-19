package uz.urspi.newurspi.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Schema(description = "News create/update dto")
public class NewsDTO {

    @NotEmpty(message = "News title (uz) cannot be empty")
    @Schema(description = "News title in Uzbek")
    private String titleUz;

    @Schema(description = "News title in Russian")
    private String titleRu;

    @Schema(description = "News title in English")
    private String titleEn;

    @Schema(description = "News content in Uzbek")
    private String contentUz;

    @Schema(description = "News content in Russian")
    private String contentRu;

    @Schema(description = "News content in English")
    private String contentEn;

    @Schema(description = "Author name")
    private String author;

    @Schema(description = "Main/cover image file")
    private MultipartFile mainImage;

    @Schema(description = "Additional image files (up to 5)")
    private List<MultipartFile> images;
}
