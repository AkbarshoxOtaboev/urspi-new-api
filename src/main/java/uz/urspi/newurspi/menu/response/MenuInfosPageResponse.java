package uz.urspi.newurspi.menu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.menu.MenuContentType;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfosPageResponse {
    private MenuSubCategoryLocalizedResponse subCategory;
    private MenuContentType contentType;
    private String linkPath;
    private String linkTargetType;
    private List<MenuInfoLocalizedResponse> infos;
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;
}
