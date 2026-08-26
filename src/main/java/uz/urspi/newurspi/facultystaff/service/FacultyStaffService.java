package uz.urspi.newurspi.facultystaff.service;

import uz.urspi.newurspi.facultystaff.dto.FacultyStaffDTO;
import uz.urspi.newurspi.facultystaff.response.FacultyStaffLocalizedResponse;
import uz.urspi.newurspi.facultystaff.response.FacultyStaffResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface FacultyStaffService {
    FacultyStaffResponse create(FacultyStaffDTO dto);

    FacultyStaffResponse findById(Long id);

    List<FacultyStaffResponse> fetchAll();

    List<FacultyStaffLocalizedResponse> fetchAllByLang(Language lang);

    List<FacultyStaffLocalizedResponse> fetchByFacultyIdByLang(Long facultyId, Language lang);

    FacultyStaffResponse update(Long id, FacultyStaffDTO dto);

    void delete(Long id);

    void activeOrDisabled(Long id);
}
