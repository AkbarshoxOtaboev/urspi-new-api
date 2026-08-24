package uz.urspi.newurspi.greeninstitute.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Green institute localized response")
public class GreenInstituteLocalizedResponse {
    private Long id;
    private String title;
    private List<String> imageLinks;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
