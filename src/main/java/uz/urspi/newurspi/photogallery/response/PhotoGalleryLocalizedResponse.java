package uz.urspi.newurspi.photogallery.response;

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
@Schema(description = "Photo gallery localized response")
public class PhotoGalleryLocalizedResponse {
    private Long id;
    private String title;
    private String description;
    private String imageLink;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
