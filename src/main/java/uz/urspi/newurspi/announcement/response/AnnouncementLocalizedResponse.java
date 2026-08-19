package uz.urspi.newurspi.announcement.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Announcement localized response")
public class AnnouncementLocalizedResponse {
    private Long id;
    private String title;
    private String content;
    private String imageLink;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
