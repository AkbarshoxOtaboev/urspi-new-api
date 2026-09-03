package uz.urspi.newurspi.announcement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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

    @NotNull(message = "Published date cannot be null")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Manual publish date (dd-MM-yyyy)", example = "01-01-2026")
    private LocalDate publishedAt;

    @Schema(description = "Announcement image file")
    private MultipartFile image;
}
