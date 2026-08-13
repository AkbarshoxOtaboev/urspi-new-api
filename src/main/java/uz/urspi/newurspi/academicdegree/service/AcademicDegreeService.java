package uz.urspi.newurspi.academicdegree.service;

import uz.urspi.newurspi.academicdegree.dto.AcademicDegreeDTO;
import uz.urspi.newurspi.academicdegree.response.AcademicDegreeResponse;

import java.util.List;

public interface AcademicDegreeService {
    AcademicDegreeResponse create(AcademicDegreeDTO dto);
    AcademicDegreeResponse findById(Long id);
    List<AcademicDegreeResponse> fetchAllAcademicDegrees();
    AcademicDegreeResponse update(Long id, AcademicDegreeDTO dto);
    void delete(Long id);
    void activeOrDisabledAcademicDegree(Long id);
}
