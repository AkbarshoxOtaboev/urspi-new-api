package uz.urspi.newurspi.menu.service;

import uz.urspi.newurspi.menu.dto.MenuDTO;
import uz.urspi.newurspi.menu.response.MenuResponse;

import java.util.List;

public interface MenuService {
    MenuResponse create(MenuDTO dto);
    MenuResponse findById(Long id);
    List<MenuResponse> fetchAll();
    MenuResponse update(Long id, MenuDTO dto);
    void delete(Long id);
    void changeStatus(Long id);
}
