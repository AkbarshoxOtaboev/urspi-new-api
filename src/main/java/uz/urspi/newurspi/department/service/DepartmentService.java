package uz.urspi.newurspi.department.service;

import uz.urspi.newurspi.department.dto.DepartmentDTO;
import uz.urspi.newurspi.department.response.DepartmentLocalizedResponse;
import uz.urspi.newurspi.department.response.DepartmentResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse create(DepartmentDTO dto);
    DepartmentResponse findById(Long id);
    List<DepartmentResponse> fetchAllDepartments();
    List<DepartmentResponse> fetchByFacultyId(Long facultyId);
    DepartmentResponse update(Long id, DepartmentDTO dto);
    void delete(Long id);
    void activeOrDisabledDepartment(Long id);
    List<DepartmentLocalizedResponse> fetchAllDepartmentsByLang(Language lang);
    List<DepartmentLocalizedResponse> fetchByFacultyIdAndLang(Long facultyId, Language lang);
}
