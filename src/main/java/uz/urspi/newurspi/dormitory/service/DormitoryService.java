package uz.urspi.newurspi.dormitory.service;

import uz.urspi.newurspi.dormitory.dto.DormitoryDTO;
import uz.urspi.newurspi.dormitory.response.DormitoryLocalizedResponse;
import uz.urspi.newurspi.dormitory.response.DormitoryResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface DormitoryService {
    DormitoryResponse create(DormitoryDTO dto);
    DormitoryResponse findById(Long id);
    List<DormitoryResponse> fetchAll();
    List<DormitoryLocalizedResponse> fetchAllByLang(Language lang);
    DormitoryResponse update(Long id, DormitoryDTO dto);
    void delete(Long id);
    void activeOrDisabled(Long id);
}
