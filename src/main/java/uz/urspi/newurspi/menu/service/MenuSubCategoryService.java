package uz.urspi.newurspi.menu.service;

import uz.urspi.newurspi.menu.dto.MenuSubCategoryDTO;
import uz.urspi.newurspi.menu.response.MenuSubCategoryResponse;

import java.util.List;

public interface MenuSubCategoryService {
    MenuSubCategoryResponse create(MenuSubCategoryDTO dto);
    MenuSubCategoryResponse findById(Long id);
    List<MenuSubCategoryResponse> fetchAll(Long categoryId);
    MenuSubCategoryResponse update(Long id, MenuSubCategoryDTO dto);
    void delete(Long id);
    void changeStatus(Long id);
}
