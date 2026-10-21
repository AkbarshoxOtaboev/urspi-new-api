package uz.urspi.newurspi.menu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoLocalizedResponse {
    private Long id;
    private Long subCategoryId;
    private String title;
    private String content;
    private String photoLink;
    private LocalDate publishedAt;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private List<Integer> years;
}
