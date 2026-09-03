package uz.urspi.newurspi.teacher.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponse;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Teacher localized response")
public class TeacherLocalizedResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String photoLink;
    private String cvLink;
    private Integer sortOrder;
    private List<ScientificArticleResponse> scientificArticles;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
