package uz.urspi.newurspi.greeninstitute.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Schema(description = "Green institute create/update dto")
public class GreenInstituteDTO {

    @NotEmpty(message = "Title (uz) cannot be empty")
    private String titleUz;
    private String titleRu;
    private String titleEn;

    @Schema(description = "Gallery images")
    private List<MultipartFile> images;
}
