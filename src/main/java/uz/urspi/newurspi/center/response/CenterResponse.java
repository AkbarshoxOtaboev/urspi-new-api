package uz.urspi.newurspi.center.response;

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
@Schema(description = "Center response")
public class CenterResponse {
    private Long id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
