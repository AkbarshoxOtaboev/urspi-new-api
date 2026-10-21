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
public class MenuMaterialResponse {
    private Long id;
    private Long infoId;
    private Integer year;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String fileLink;
    private String coverLink;
    private LocalDate publishedAt;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
