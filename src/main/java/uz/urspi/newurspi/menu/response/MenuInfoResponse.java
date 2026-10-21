package uz.urspi.newurspi.menu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoResponse {
    private Long id;
    private Long subCategoryId;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String contentUz;
    private String contentRu;
    private String contentEn;
    private String photoLink;
    private LocalDate publishedAt;
    private Integer sortOrder;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
