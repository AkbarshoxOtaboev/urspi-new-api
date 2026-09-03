package uz.urspi.newurspi.scientificarticle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.urspi.newurspi.utils.ScientificArticleType;

@Getter
@Setter
@Schema(description = "Teacher scientific article create/update dto")
public class ScientificArticleDTO {

    @NotNull(message = "Teacher id cannot be null")
    @Schema(description = "Teacher id")
    private Long teacherId;

    @NotEmpty(message = "Article title cannot be empty")
    @Schema(description = "Article title")
    private String title;

    @NotNull(message = "Article type cannot be null")
    @Schema(description = "Article type: XALQARO or MAHALLIY")
    private ScientificArticleType type;

    @NotNull(message = "Publication year cannot be null")
    @Schema(description = "Publication year", example = "2024")
    private Integer publicationYear;

    @NotEmpty(message = "Journal/conference name cannot be empty")
    @Schema(description = "Journal or conference name")
    private String journalName;

    @Schema(description = "Article URL or DOI")
    private String articleUrl;

    @Schema(description = "PDF file")
    private MultipartFile file;
}
