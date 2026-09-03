package uz.urspi.newurspi.announcement.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Announcement response")
public class AnnouncementResponse {
    private Long id;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String contentUz;
    private String contentRu;
    private String contentEn;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate publishedAt;
    private String imageLink;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
