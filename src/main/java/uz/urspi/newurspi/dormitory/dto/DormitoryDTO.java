package uz.urspi.newurspi.dormitory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Dormitory create/update dto")
public class DormitoryDTO {

    @NotEmpty(message = "Dormitory title (uz) cannot be empty")
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private MultipartFile image;
}
