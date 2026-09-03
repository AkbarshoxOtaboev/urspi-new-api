package uz.urspi.newurspi.scientificarticle.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.ScientificArticleType;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Scientific article response")
public class ScientificArticleResponse {
    private Long id;
    private Long teacherId;
    private String title;
    private ScientificArticleType type;
    private Integer publicationYear;
    private String journalName;
    private String articleUrl;
    private String fileLink;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
