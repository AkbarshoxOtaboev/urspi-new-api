package uz.urspi.newurspi.menu.service;

import uz.urspi.newurspi.menu.dto.MenuMaterialDTO;
import uz.urspi.newurspi.menu.response.MenuMaterialResponse;

import java.util.List;

public interface MenuMaterialService {
    MenuMaterialResponse create(MenuMaterialDTO dto);
    MenuMaterialResponse findById(Long id);
    List<MenuMaterialResponse> fetchAll(Long infoId, Integer year);
    MenuMaterialResponse update(Long id, MenuMaterialDTO dto);
    void delete(Long id);
    void changeStatus(Long id);
}
