package uz.urspi.newurspi.degree.service;

import uz.urspi.newurspi.degree.dto.DegreeDTO;
import uz.urspi.newurspi.degree.response.DegreeResponse;

import java.util.List;

public interface DegreeService {
    DegreeResponse create(DegreeDTO dto);
    DegreeResponse findById(Long id);
    List<DegreeResponse> fetchAllDegrees();
    DegreeResponse update(Long id, DegreeDTO dto);
    void delete(Long id);
    void activeOrDisabledDegree(Long id);
}
