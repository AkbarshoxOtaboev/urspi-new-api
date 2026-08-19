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
@Schema(description = "News response")
public class NewsResponse {
    private Long id;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String contentUz;
    private String contentRu;
    private String contentEn;
    private String author;
    private String mainImageLink;
    private List<String> imageLinks;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
