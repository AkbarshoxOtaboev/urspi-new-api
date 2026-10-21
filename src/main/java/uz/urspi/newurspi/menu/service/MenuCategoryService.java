package uz.urspi.newurspi.menu.service;

import uz.urspi.newurspi.menu.dto.MenuCategoryDTO;
import uz.urspi.newurspi.menu.response.MenuCategoryResponse;

import java.util.List;

public interface MenuCategoryService {
    MenuCategoryResponse create(MenuCategoryDTO dto);
    MenuCategoryResponse findById(Long id);
    List<MenuCategoryResponse> fetchAll(Long menuId);
    MenuCategoryResponse update(Long id, MenuCategoryDTO dto);
    void delete(Long id);
    void changeStatus(Long id);
}
