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
@Schema(description = "Dormitory localized response")
public class DormitoryLocalizedResponse {
    private Long id;
    private String title;
    private String description;
    private String imageLink;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
