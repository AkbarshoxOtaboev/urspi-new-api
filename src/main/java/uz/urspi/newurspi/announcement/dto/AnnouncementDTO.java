package uz.urspi.newurspi.announcement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Announcement create/update dto")
public class AnnouncementDTO {

    @NotEmpty(message = "Announcement title (uz) cannot be empty")
    @Schema(description = "Announcement title in Uzbek")
    private String titleUz;

    @Schema(description = "Announcement title in Russian")
    private String titleRu;

    @Schema(description = "Announcement title in English")
    private String titleEn;

    @Schema(description = "Announcement content in Uzbek")
    private String contentUz;

    @Schema(description = "Announcement content in Russian")
    private String contentRu;

    @Schema(description = "Announcement content in English")
    private String contentEn;

    @Schema(description = "Announcement image file")
    private MultipartFile image;
}
