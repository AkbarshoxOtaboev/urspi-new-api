package uz.urspi.newurspi.menu.service;

import uz.urspi.newurspi.menu.dto.MenuInfoDTO;
import uz.urspi.newurspi.menu.response.MenuInfoResponse;

import java.util.List;

public interface MenuInfoService {
    MenuInfoResponse create(MenuInfoDTO dto);
    MenuInfoResponse findById(Long id);
    List<MenuInfoResponse> fetchAll(Long subCategoryId);
    MenuInfoResponse update(Long id, MenuInfoDTO dto);
    void delete(Long id);
    void changeStatus(Long id);
}
