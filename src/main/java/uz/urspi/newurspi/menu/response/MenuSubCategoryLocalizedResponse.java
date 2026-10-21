package uz.urspi.newurspi.menu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.menu.MenuContentType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuSubCategoryLocalizedResponse {
    private Long id;
    private Long categoryId;
    private String name;
    private String slug;
    private MenuContentType contentType;
    private String tableColumns;
    private String linkPath;
    private String linkTargetType;
    private Integer sortOrder;
}
