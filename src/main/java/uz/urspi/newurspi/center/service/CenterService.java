package uz.urspi.newurspi.center.service;

import uz.urspi.newurspi.center.dto.CenterDTO;
import uz.urspi.newurspi.center.response.CenterLocalizedResponse;
import uz.urspi.newurspi.center.response.CenterResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface CenterService {
    CenterResponse create(CenterDTO dto);
    CenterResponse findById(Long id);
    List<CenterResponse> fetchAllCenters();
    List<CenterLocalizedResponse> fetchAllCentersByLang(Language lang);
    CenterResponse update(Long id, CenterDTO dto);
    void delete(Long id);
    void activeOrDisabledCenter(Long id);
}
