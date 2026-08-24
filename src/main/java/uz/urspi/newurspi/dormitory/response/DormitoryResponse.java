package uz.urspi.newurspi.dormitory.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dormitory response")
public class DormitoryResponse {
    private Long id;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String imageLink;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
