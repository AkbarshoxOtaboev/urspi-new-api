package uz.urspi.newurspi.greeninstitute.service;

import uz.urspi.newurspi.greeninstitute.dto.GreenInstituteDTO;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteLocalizedResponse;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface GreenInstituteService {
    GreenInstituteResponse create(GreenInstituteDTO dto);
    GreenInstituteResponse findById(Long id);
    List<GreenInstituteResponse> fetchAll();
    List<GreenInstituteLocalizedResponse> fetchAllByLang(Language lang);
    GreenInstituteResponse update(Long id, GreenInstituteDTO dto);
    void delete(Long id);
    void activeOrDisabled(Long id);
}
