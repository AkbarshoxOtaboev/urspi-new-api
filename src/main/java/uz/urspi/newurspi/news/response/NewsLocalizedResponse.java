package uz.urspi.newurspi.news.response;

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
@Schema(description = "News localized response")
public class NewsLocalizedResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String mainImageLink;
    private List<String> imageLinks;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
