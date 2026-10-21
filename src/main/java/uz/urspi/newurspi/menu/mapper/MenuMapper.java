package uz.urspi.newurspi.menu.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.menu.Menu;
import uz.urspi.newurspi.menu.MenuCategory;
import uz.urspi.newurspi.menu.MenuInfo;
import uz.urspi.newurspi.menu.MenuMaterial;
import uz.urspi.newurspi.menu.MenuSubCategory;
import uz.urspi.newurspi.menu.response.MenuCategoryLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuCategoryResponse;
import uz.urspi.newurspi.menu.response.MenuInfoLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuInfoResponse;
import uz.urspi.newurspi.menu.response.MenuLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuMaterialLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuMaterialResponse;
import uz.urspi.newurspi.menu.response.MenuResponse;
import uz.urspi.newurspi.menu.response.MenuSubCategoryLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuSubCategoryResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {

    public MenuResponse toResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .nameUz(menu.getNameUz())
                .nameRu(menu.getNameRu())
                .nameEn(menu.getNameEn())
                .slug(menu.getSlug())
                .sortOrder(menu.getSortOrder())
                .status(menu.getStatus())
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .build();
    }

    public List<MenuResponse> toMenuResponseList(List<Menu> menus) {
        return menus.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuLocalizedResponse toLocalized(Menu menu, Language lang) {
        return MenuLocalizedResponse.builder()
                .id(menu.getId())
                .name(pick(menu.getNameUz(), menu.getNameRu(), menu.getNameEn(), lang))
                .slug(menu.getSlug())
                .sortOrder(menu.getSortOrder())
                .build();
    }

    public List<MenuLocalizedResponse> toMenuLocalizedList(List<Menu> menus, Language lang) {
        return menus.stream().map(m -> toLocalized(m, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuCategoryResponse toResponse(MenuCategory category) {
        return MenuCategoryResponse.builder()
                .id(category.getId())
                .menuId(category.getMenu().getId())
                .nameUz(category.getNameUz())
                .nameRu(category.getNameRu())
                .nameEn(category.getNameEn())
                .slug(category.getSlug())
                .sortOrder(category.getSortOrder())
                .status(category.getStatus())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public List<MenuCategoryResponse> toCategoryResponseList(List<MenuCategory> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuCategoryLocalizedResponse toLocalized(MenuCategory category, Language lang) {
        return MenuCategoryLocalizedResponse.builder()
                .id(category.getId())
                .menuId(category.getMenu().getId())
                .name(pick(category.getNameUz(), category.getNameRu(), category.getNameEn(), lang))
                .slug(category.getSlug())
                .sortOrder(category.getSortOrder())
                .build();
    }

    public List<MenuCategoryLocalizedResponse> toCategoryLocalizedList(List<MenuCategory> list, Language lang) {
        return list.stream().map(c -> toLocalized(c, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuSubCategoryResponse toResponse(MenuSubCategory sub) {
        return MenuSubCategoryResponse.builder()
                .id(sub.getId())
                .categoryId(sub.getCategory().getId())
                .nameUz(sub.getNameUz())
                .nameRu(sub.getNameRu())
                .nameEn(sub.getNameEn())
                .slug(sub.getSlug())
                .contentType(sub.getContentType())
                .tableColumns(sub.getTableColumns())
                .linkPath(sub.getLinkPath())
                .linkTargetType(sub.getLinkTargetType())
                .sortOrder(sub.getSortOrder())
                .status(sub.getStatus())
                .createdAt(sub.getCreatedAt())
                .updatedAt(sub.getUpdatedAt())
                .build();
    }

    public List<MenuSubCategoryResponse> toSubCategoryResponseList(List<MenuSubCategory> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuSubCategoryLocalizedResponse toLocalized(MenuSubCategory sub, Language lang) {
        return MenuSubCategoryLocalizedResponse.builder()
                .id(sub.getId())
                .categoryId(sub.getCategory().getId())
                .name(pick(sub.getNameUz(), sub.getNameRu(), sub.getNameEn(), lang))
                .slug(sub.getSlug())
                .contentType(sub.getContentType())
                .tableColumns(sub.getTableColumns())
                .linkPath(sub.getLinkPath())
                .linkTargetType(sub.getLinkTargetType())
                .sortOrder(sub.getSortOrder())
                .build();
    }

    public List<MenuSubCategoryLocalizedResponse> toSubCategoryLocalizedList(List<MenuSubCategory> list, Language lang) {
        return list.stream().map(s -> toLocalized(s, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuInfoResponse toResponse(MenuInfo info) {
        return MenuInfoResponse.builder()
                .id(info.getId())
                .subCategoryId(info.getSubCategory().getId())
                .titleUz(info.getTitleUz())
                .titleRu(info.getTitleRu())
                .titleEn(info.getTitleEn())
                .contentUz(info.getContentUz())
                .contentRu(info.getContentRu())
                .contentEn(info.getContentEn())
                .photoLink(info.getPhotoLink())
                .publishedAt(info.getPublishedAt())
                .sortOrder(info.getSortOrder())
                .status(info.getStatus())
                .createdAt(info.getCreatedAt())
                .updatedAt(info.getUpdatedAt())
                .build();
    }

    public List<MenuInfoResponse> toInfoResponseList(List<MenuInfo> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuInfoLocalizedResponse toLocalized(MenuInfo info, Language lang, List<Integer> years) {
        return MenuInfoLocalizedResponse.builder()
                .id(info.getId())
                .subCategoryId(info.getSubCategory().getId())
                .title(pick(info.getTitleUz(), info.getTitleRu(), info.getTitleEn(), lang))
                .content(pick(info.getContentUz(), info.getContentRu(), info.getContentEn(), lang))
                .photoLink(info.getPhotoLink())
                .publishedAt(info.getPublishedAt())
                .sortOrder(info.getSortOrder())
                .createdAt(info.getCreatedAt())
                .years(years)
                .build();
    }

    public MenuInfoLocalizedResponse toLocalized(MenuInfo info, Language lang) {
        return toLocalized(info, lang, null);
    }

    public List<MenuInfoLocalizedResponse> toInfoLocalizedList(List<MenuInfo> list, Language lang) {
        return list.stream().map(i -> toLocalized(i, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuMaterialResponse toResponse(MenuMaterial material) {
        return MenuMaterialResponse.builder()
                .id(material.getId())
                .infoId(material.getInfo().getId())
                .year(material.getYear())
                .titleUz(material.getTitleUz())
                .titleRu(material.getTitleRu())
                .titleEn(material.getTitleEn())
                .fileLink(material.getFileLink())
                .coverLink(material.getCoverLink())
                .publishedAt(material.getPublishedAt())
                .status(material.getStatus())
                .createdAt(material.getCreatedAt())
                .updatedAt(material.getUpdatedAt())
                .build();
    }

    public List<MenuMaterialResponse> toMaterialResponseList(List<MenuMaterial> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public MenuMaterialLocalizedResponse toLocalized(MenuMaterial material, Language lang) {
        return MenuMaterialLocalizedResponse.builder()
                .id(material.getId())
                .infoId(material.getInfo().getId())
                .year(material.getYear())
                .title(pick(material.getTitleUz(), material.getTitleRu(), material.getTitleEn(), lang))
                .fileLink(material.getFileLink())
                .coverLink(material.getCoverLink())
                .publishedAt(material.getPublishedAt())
                .createdAt(material.getCreatedAt())
                .build();
    }

    public List<MenuMaterialLocalizedResponse> toMaterialLocalizedList(List<MenuMaterial> list, Language lang) {
        return list.stream().map(m -> toLocalized(m, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    private String pick(String uz, String ru, String en, Language lang) {
        return switch (lang) {
            case ru -> ru != null && !ru.isBlank() ? ru : uz;
            case en -> en != null && !en.isBlank() ? en : uz;
            default -> uz;
        };
    }
}
