package uz.urspi.newurspi.menu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryResponse {
    private Long id;
    private Long menuId;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String slug;
    private Integer sortOrder;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
